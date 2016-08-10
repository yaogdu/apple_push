package com.demai.util;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dear on 16/5/13.
 */
public class DataHolder {

    /**
     * 错误消息次数缓存  msgId-->errorTimes
     */
    public volatile static Map<Integer,Integer> msgErrorTimes = new ConcurrentHashMap<>();
}
