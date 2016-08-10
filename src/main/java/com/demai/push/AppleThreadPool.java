package com.demai.push;

import com.relayrides.pushy.apns.ApnsPushNotification;
import com.relayrides.pushy.apns.PushNotificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class AppleThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(AppleThreadPool.class);

    public AppleThreadPool() {
        service = Executors.newFixedThreadPool(EXECUTOR_SIZE);
        logger.info("ExecutorService:" + service);
    }

    private int EXECUTOR_SIZE = 10;
    public ExecutorService service;

    public void submit(Callable thread) {
        service.submit(thread);
    }

    public Future<Integer> submitWithReturn(Callable<Integer> thread) {
        return service.submit(thread);
    }

    public Future<io.netty.util.concurrent.Future<PushNotificationResponse<ApnsPushNotification>>>
    submitWithFutureReturn
            (Callable<io.netty.util.concurrent.Future<PushNotificationResponse<ApnsPushNotification>>> thread) {

        return service.submit(thread);
    }

}
