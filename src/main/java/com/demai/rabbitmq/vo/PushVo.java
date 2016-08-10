package com.demai.rabbitmq.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by dear on 16/7/29.
 */
public class PushVo implements Serializable {

    private int type;
    private long userId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(long followedUserId) {
        this.followedUserId = followedUserId;
    }

    public long getUserId() {
        return userId;

    }
    public String toString(){
        return JSON.toJSONString(this);
    }

    private long followedUserId;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(long favoriteId) {
        this.favoriteId = favoriteId;
    }

    private long targetUserId;
    private long questionId;
    private long favoriteId;

}
