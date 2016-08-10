package com.demai.push;

import com.demai.engine.impl.AndroidEngine;
import com.demai.engine.impl.PushyEngine;
import com.demai.rabbitmq.vo.PushVo;
import com.demai.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by dear on 16/7/28.
 */
@Component
public class Task implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    @Resource
    PushyEngine pushyEngine;

    @Resource
    AndroidEngine androidEngine;

    @Resource
    AndroidThreadPool androidThreadPool;

    @Resource
    AppleThreadPool appleThreadPool;

    @Resource
    RedisUtil redisUtil;

    private final String noticeSetKey = ".notice.set";

    private LinkedList<PushVo> voPool = new LinkedList<PushVo>();

    private PushVo getVo() {
        synchronized (voPool) {
            if (voPool != null && voPool.size() > 0) {
                return voPool.removeFirst();
            } else {
                return null;
            }
        }
    }

    public void addMsg(PushVo vo) {
        synchronized (voPool) {
            voPool.add(vo);
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
        Iterator<PushVo> vos = voPool.iterator();
        while (vos.hasNext()) {
            final PushVo vo = getVo();
            try {


            } catch (Exception e) {
                logger.error("error msg is {}", vo);
                logger.error("run error", e);
            }
        }
    }
}
