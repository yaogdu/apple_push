package com.demai.rabbitmq;

import com.demai.common.Config;
import com.demai.rabbitmq.shard.Node;
import com.demai.rabbitmq.shard.Shard;
import com.demai.rabbitmq.vo.MsgObject;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * mq消息发送器
 *
 * @author duayaoguang
 */
@Component
public class Sender {
    private final static Logger logger = LoggerFactory.getLogger(Sender.class);

    private ConnectionFactory connectionFactory;

    private static int count = 10;

    private static ConnectionFactory factory;

    private static List<Node> exchanges = new ArrayList<Node>(); // 真实节点

    private static Shard<Node> exchange;

    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    private static String exchangeName = "";

    final static ExecutorService pool = Executors.newFixedThreadPool(2);

    private static ConcurrentHashMap<MsgObject, Integer> errorMsgMap = new ConcurrentHashMap<>();//key 消息 value 错误次数

    private void channelRecovery() throws IOException {
        try {
            factory = new ConnectionFactory();
            factory.setUsername(Config.get("rabbitmq.send.username"));
            factory.setPassword(Config.get("rabbitmq.send.pwd"));
            factory.setHost(Config.get("rabbitmq.send.addr"));
            factory.setPort(Integer.parseInt(Config.get("rabbitmq.send.port")));
            factory.setAutomaticRecoveryEnabled(true);
            factory.setTopologyRecoveryEnabled(true);
            factory.setConnectionTimeout(1000);
            factory.setNetworkRecoveryInterval(5);
            factory.setRequestedHeartbeat(10);
            initConnection();

            logger.info("factory of sender initialed successfully..");

        } catch (Exception e) {
            try {
                Thread.sleep(factory.getNetworkRecoveryInterval() * 1000);
                logger.error("sender channelRecovery error occurred", e);
                channelRecovery();
            } catch (InterruptedException e1) {
                logger.error("sender init factory error", e);
                channelRecovery();
            }
        }

    }

    private void initConnection() {

        try {

            for (int i = 0; i < count; i++) {
                final String exchangeName = this.exchangeName + i;

                Connection connection = factory.newConnection(pool);

                connection.addShutdownListener(new ShutdownListener() {
                    @Override
                    public void shutdownCompleted(ShutdownSignalException e) {
                        logger.error("connection has been shut down by", e);
                    }
                });

                final Channel channel = connection.createChannel(10);
                channel.exchangeDeclare(exchangeName, "fanout", true);
                channel.confirmSelect();
                channel.addShutdownListener(new ShutdownListener() {
                    @Override
                    public void shutdownCompleted(ShutdownSignalException e) {
                        logger.error("channel has been shut down by", e);
                    }
                });
                channelMap.put(exchangeName, channel);
            }

        } catch (IOException e) {
            logger.error("IOException occurred when init connection", e);
        } catch (Exception e) {
            logger.error("Exception occurred when init connection", e);
        }

    }

    private void initShard() {
        try {

            for (int i = 0; i < count; i++) {
                Node s1 = new Node(new Long(i), Config.get("rabbitmq.send.exchange")
                        + i);
                exchanges.add(s1);
            }

            exchange = new Shard<>(exchanges);//初始化shard信息
            exchange.printShards();

        } catch (Exception e) {
            logger.error("error occurred when recovery sender connection", e);
        }
    }

    public Sender() {
        try {

            this.exchangeName = Config.get("rabbitmq.send.exchange");
            this.count = Integer.parseInt(Config.get("rabbitmq.send.count"));
            // 创建连接和频道
            initShard();
            channelRecovery();//init channel and consumer
            logger.info("init sender successfully");
        } catch (Exception e) {
            logger.error("init error", e);
        }

    }

    public void run() throws Exception {
        try {
            logger.info("rabbitmq sender run..");

        } catch (Throwable e) {
            logger.error("foobar :(", e);
        }
    }

    public static String getExchange(Long key) {

        String exchangeName = exchange.getNode(key.toString()).getExchangeName();

        return exchangeName;
    }

    public void send(final String topic, final MsgObject msg) {
        try {

            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        long toId = msg.getToId();
                        if (toId > 0) {
                            String exchangeName = getExchange(toId);

                            logger.info("trying to send msg via exchangeName :{}", exchangeName);

                            Channel channel = channelMap.get(exchangeName);

                            if (channel != null && !channel.isOpen()) {
                                logger.info("channel is trying to basicRecover(true)");
                                channel.basicRecover(true);
                            }

                            if (channel.getConnection() != null
                                    && !channel.getConnection().isOpen()) {
                                logger.info("channel.getConnection is trying to recovery");
                                channelRecovery();
                                requeue(topic, msg);
                            }

                            channel.basicPublish(exchangeName, "",
                                    MessageProperties.MINIMAL_PERSISTENT_BASIC, msg.toString()
                                            .getBytes());
                            logger.info("published msg {}", msg);

                        }

                    } catch (IOException e) {
                        logger.info("publish msg error {}", msg, e);
                        try {
                            channelRecovery();//init channel and consumer
                            requeue(topic, msg);
                        } catch (IOException e1) {
                            logger.error("channelRecovery error", e1);
                        }

                    }
                }
            });

            logger.info("rabbitmq sent msg {},topic {}", msg, topic);
        } catch (Exception e) {
            logger.error("rabbitmq send 异常 {}", msg, e);
        }

    }

    private void requeue(final String topic, final MsgObject msg) {
        final int requeueCount = 5;//max requeue times per msg

        try {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    synchronized (errorMsgMap) {
                        if (errorMsgMap.containsKey(msg)) {
                            Integer errorValue = errorMsgMap.get(msg);
                            if (errorValue >= requeueCount) {
                                errorMsgMap.remove(msg);
                                logger.info(
                                        "sending error of msg {}  has occurred more than {} times,abort to requeue it.",
                                        msg, requeueCount);

                            } else if (errorValue < requeueCount) {
                                errorMsgMap.put(msg, errorValue + 1);
                                logger.info("the {} times to requeue msg {}", errorValue + 1, msg);
                                send(topic, msg);//requeue the msg
                            }
                        } else {
                            errorMsgMap.put(msg, 1);
                            logger.info("the {} times to requeue msg {}", 1, msg);
                            send(topic, msg);//requeue the msg
                        }
                    }
                }
            });

        } catch (Exception e) {
            logger.error("requeue msg {} error,", msg, e);
        }

    }


}
