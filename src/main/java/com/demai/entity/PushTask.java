package com.demai.entity;

/**
 * Created by dear on 16/5/11.
 */

import com.demai.rabbitmq.vo.MsgObject;

/**
 * base task
 */
public class PushTask {

    protected PushTask() {

    }

    public PushTask(String token, String title, String message, int badge, String action, Long uid, int type, int app, String
            appVersion, MsgObject msg) {
        this.token = token;
        this.message = message;
        this.badge = badge;
        this.action = action;
        this.uid = uid;
        this.type = type;
        this.app = app;
        this.appVersion = appVersion;
        this.msg = msg;
    }

    public MsgObject msg;
    public String action;
    public String title;
    public int badge;
    public String token;
    public String message;
    public Long uid;
    public int type;
    public int app;
    public String appVersion;
}
