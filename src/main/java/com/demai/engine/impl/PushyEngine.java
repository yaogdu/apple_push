package com.demai.engine.impl;

import com.demai.common.Constants;
import com.demai.engine.PushEngine;
import com.demai.engine.util.PushyUtil;
import com.demai.entity.AppleTask;
import com.demai.entity.PushTask;
import com.demai.util.BigLog;
import com.relayrides.pushy.apns.ApnsPushNotification;
import com.relayrides.pushy.apns.PushNotificationResponse;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dear on 16/5/22.
 */
@Component
public class PushyEngine implements PushEngine, Callable<Future<PushNotificationResponse<ApnsPushNotification>>> {

    ExecutorService es = Executors.newFixedThreadPool(2);

    private static final Logger logger = LoggerFactory.getLogger(PushyEngine.class);

    private LinkedList<AppleTask> taskPool = new LinkedList<AppleTask>();

    private AppleTask getTask() {
        synchronized (taskPool) {
            if (taskPool != null && taskPool.size() > 0) {
                return taskPool.removeFirst();
            } else {
                return null;
            }
        }
    }

    public void addTask(PushTask pushTask) {
        synchronized (taskPool) {
            taskPool.add(new AppleTask(pushTask.token, pushTask.title, pushTask.message, pushTask.badge, pushTask.action,
                    pushTask.uid, pushTask.type, pushTask.app, pushTask.appVersion));
        }
    }

    public Future<PushNotificationResponse<ApnsPushNotification>> sendMessage(AppleTask task, SimpleApnsPushNotification
            notification) {
        Future<PushNotificationResponse<ApnsPushNotification>> future = null;
        try {

            future = PushyUtil
                    .apnsClient.sendNotification(notification);

        } catch (Exception e) {
            logger.error("Failed to send push notification.");
            sendMessage(task, notification);
        }
        return future;
    }


    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Future<PushNotificationResponse<ApnsPushNotification>> call() throws Exception {
        Iterator<AppleTask> tasks = taskPool.iterator();
        while (tasks.hasNext()) {
            final AppleTask task = getTask();
            try {
                final ApnsPayloadBuilder builder = new ApnsPayloadBuilder();
//                final String payload = builder.setAlertTitle(task.title).setBadgeNumber(task.badge)
//                        .setSoundFileName("default").setAlertBody(task.message).addCustomProperty("scheme", task
//                                .action).buildWithDefaultMaximumLength();

                final String payload = builder.setAlertTitle("title").setBadgeNumber(2)
                        .setSoundFileName("default").setAlertBody(task.message).addCustomProperty("scheme", task
                                .action).buildWithDefaultMaximumLength();
                final String token = TokenUtil.sanitizeTokenString(task.token);

                logger.info("token is {} and payload is {}", token, payload);

                SimpleApnsPushNotification notification = new SimpleApnsPushNotification(token, Constants
                        .IOS_TOPIC,
                        payload);
                final Future<PushNotificationResponse<ApnsPushNotification>> future = sendMessage(task, notification);
                es.submit(new Runnable() {
                    @Override
                    public void run() {
                        BigLog.log(task, future);
                    }
                });
                //BigLog.log(task,future);
                return future;
            } catch (Exception e) {
                logger.error("error token is {}", task.token);
                logger.error("run error occurred", e);
            }
        }
        return null;


    }

}
