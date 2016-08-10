package com.demai.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class AndroidThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(AndroidThreadPool.class);

    public AndroidThreadPool() {
        service = Executors.newFixedThreadPool(EXECUTOR_SIZE);
        logger.info("ExecutorService:" + service);
    }

    private int EXECUTOR_SIZE = 10;
    public ExecutorService service;

    public void submit(Runnable thread) {
        service.submit(thread);
    }

}
