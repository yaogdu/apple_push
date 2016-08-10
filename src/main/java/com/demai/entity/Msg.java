package com.demai.entity;

import java.io.Serializable;

public class Msg implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int type;//通知类型
	private String content;//内容
	private String scheme;//跳转scheme
	private long operateUid;//操作用户uid
	private Object target;//目标对象，可能是用户uid，可能是feed的id，可能是uids，可能是群id
	private String versionCode;//版本号
	private int versionOperate;//版本号操作运算类型：1、小于；2、等于；3、大于；4
	private long feedId;//feedId
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public long getOperateUid() {
		return operateUid;
	}
	public void setOperateUid(long operateUid) {
		this.operateUid = operateUid;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public int getVersionOperate() {
		return versionOperate;
	}
	public void setVersionOperate(int versionOperate) {
		this.versionOperate = versionOperate;
	}
	public long getFeedId() {
		return feedId;
	}
	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}
}
