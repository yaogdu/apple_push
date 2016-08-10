package com.demai.entity;

import java.io.Serializable;

/**
 * Created by dear on 16/5/11.
 */

/**
 * apple push task
 */
public class AppleTask extends PushTask implements Serializable {

    public AppleTask(String token, String title, String message, int badge, String action, Long uid, int type, int app, String
            appVersion) {
        super(token, title, message, badge, action, uid, type, app, appVersion, null);
        this.token = token;
        this.message = message;
        this.badge = badge;
        this.action = action;
        this.uid = uid;
        this.type = type;
        this.app = app;
        this.appVersion = appVersion;
        this.msg = null;
    }

}
