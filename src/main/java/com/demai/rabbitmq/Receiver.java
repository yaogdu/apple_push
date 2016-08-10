package com.demai.rabbitmq;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.demai.common.Config;
import com.demai.push.Task;
import com.demai.push.AndroidThreadPool;
import com.demai.rabbitmq.vo.PushVo;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * Created by dear on 15/12/1.
 */
@Component
public class Receiver {

    private final static Logger logger = LoggerFactory.getLogger(Receiver.class);

    private Connection connection;

    private Channel channel;

    QueueingConsumer consumer;

    ExecutorService es = Executors.newFixedThreadPool(2);

    ConnectionFactory factory;

    private String queueName = "";

    private String exchangeName;

    boolean run = false;

    @Resource
    Task task;

    @Resource
    AndroidThreadPool pool;

    /**
     * 初始化 rabbitmq factory
     *
     * @throws IOException
     */
    private void initChannel() throws IOException {
        try {

            factory = new ConnectionFactory();
            factory.setUsername(Config.get("rabbitmq.receive.username"));
            factory.setPassword(Config.get("rabbitmq.receive.pwd"));
            factory.setHost(Config.get("rabbitmq.receive.addr"));
            factory.setPort(Integer.parseInt(Config.get("rabbitmq.receive.port")));
            factory.setAutomaticRecoveryEnabled(true);
            factory.setTopologyRecoveryEnabled(true);
            factory.setConnectionTimeout(1000);
            factory.setNetworkRecoveryInterval(5);
            factory.setRequestedHeartbeat(10);

            connection = factory.newConnection(es);
            channel = connection.createChannel(10);
            channel.exchangeDeclare(exchangeName, "direct", true);
            logger.info("exchangeName is {}", exchangeName);
            logger.info("queueName is {}", queueName);

            channel.queueDeclare(queueName, true, false, true, null);//durable,exclusive,autodelete
            channel.queueBind(queueName, exchangeName, "");

            logger.info("Waiting for messages....");

            consumer = new QueueingConsumer(channel);

            channel.basicConsume(queueName, false, consumer);

            run = true;

            logger.info("facetory of receiver inited successfully..");

        } catch (Exception e) {
            try {
                logger.error("initChannel error occurred", e);
                Thread.sleep(factory.getNetworkRecoveryInterval() * 1000);
                initChannel();
            } catch (InterruptedException e1) {
                logger.error("init factory error", e);
                initChannel();
            }
        }

    }

    /**
     * 接收并消费消息
     *
     * @throws IOException
     */
    private void consume() throws IOException {
        try {

            es.execute(new Runnable() {

                @Override
                public void run() {
                    while (run) {

                        String message = null;
                        QueueingConsumer.Delivery delivery = null;
                        try {
                            logger.info("ready to receive...");
                            delivery = consumer.nextDelivery();
                            message = new String(delivery.getBody(), "utf-8");
                            if (!StringUtils.isEmpty(message)) {
                                consumeMessage(message);
                                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                                logger.info("ack msg {}", message);

                            }
                        } catch (InterruptedException | ShutdownSignalException
                                | ConsumerCancelledException cce) {//channel Recovery
                            logger.error("connection error", cce);
                            try {
                                run = false;
                                logger.info("trying to recovery connection and channel");
                                initChannel();
                            } catch (Exception e) {
                                logger.error("channel Recovery error", e);
                            }
                        } catch (Exception e) {//channelRecovery and nack msg in order to requeue it.
                            try {
                                run = false;
                                initChannel();
                                if (delivery != null) {//requeue the message after consumeMessage error
                                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(),
                                            false, true);//requeue
                                    logger.info("nack msg : {}", message);
                                }
                            } catch (IOException e1) {
                                logger.error("basic nack error ", e1);
                            }
                            logger.info("rabbitmq subscribe message error", e);
                        }
                    }
                }
            });

        } catch (Exception e) {
            logger.error("receiver init channel error", e);
        }
    }

    public Receiver() throws IOException,
            TimeoutException, InterruptedException {

        this.exchangeName = Config.get("rabbitmq.receive.exchange");
        String queueName = Config.get("rabbitmq.receive.queuename");
        this.queueName = queueName;

        // 创建连接和频道
        logger.info("receiver initialed successfully with exchangeName {} and queueName {}",
                exchangeName, queueName);
    }

    public void run() {
        try {
            initChannel();//init channel and MsgConsumer
            consume();
        } catch (Exception e) {
            logger.error("error occurred when invoking msgConsumer", e);
        }
    }

    /**
     * 消费
     *
     * @param msg
     */
    public void consumeMessage(final String msg) {
        try {//要求不得抛出异常，这里try{}catch掉
            logger.debug("收到队列消息<--- thread:{} msg:{}", Thread.currentThread().getName(), msg);
            es.execute(new Runnable() {
                @Override
                public void run() {
                    try {

                        String body = new String(msg.getBytes(), "utf8");
                        logger.info("收到的消息属性,{}", body);
                        PushVo vo = JSONObject.parseObject(body, PushVo.class);

                        task.addMsg(vo);
                        pool.submit(task);
                        logger.info("add vo:{} to thread pool",vo);
                    } catch (JSONException e) {
                        logger.error("消息格式错误", e);
                    } catch (Exception e) {
                        logger.error("异步处理消息出错", e);
                    }
                }
            });
        } catch (Exception e) {
            logger.error("消费消息出错", e);
        }
    }


}
