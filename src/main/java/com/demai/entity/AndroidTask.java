package com.demai.entity;

/**
 * Created by dear on 16/5/11.
 */

import com.demai.rabbitmq.vo.MsgObject;

/**
 * android push task
 */
public class AndroidTask extends PushTask {
    public AndroidTask(MsgObject msg) {
        super("","","",0,"",msg.getToId(),0,0,"",msg);
        //super(token, title, message, badge, action, uid, type, app, appVersion, msg);
    }
}
