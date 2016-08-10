package com.demai.engine.impl;

import com.demai.engine.PushEngine;
import com.demai.entity.AndroidTask;
import com.demai.entity.PushTask;
import com.demai.rabbitmq.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by dear on 16/5/11.
 */

/**
 * android push engine,to be implemented..
 */
@Component
public class AndroidEngine implements PushEngine, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(AndroidEngine.class);

    private LinkedList<AndroidTask> taskPool = new LinkedList<AndroidTask>();

    @Resource
    Sender sender;

    private AndroidTask getTask() {
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
            taskPool.add(new AndroidTask(pushTask.msg));
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        Iterator<AndroidTask> tasks = taskPool.iterator();
        while (tasks.hasNext()) {
            final AndroidTask task = getTask();
            try {
                sender.send("topic", task.msg);
            } catch (Exception e) {
                logger.error("error token is {}", task.token);
                logger.error("run error occurred", e);
            }
        }
    }
}
