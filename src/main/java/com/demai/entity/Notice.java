package com.demai.entity;

import java.io.Serializable;

/**
 * 通知
 * 
 * @author qianchun
 * @date 2016年5月13日 下午12:20:58
 */
public class Notice implements Serializable{
	private static final long serialVersionUID = 1L;
	//用户id
	private long uid;
	//标题
	private String title;
	//内容
	private String body;
	//跳转scheme
	private String scheme;
	//用户设备id
	private String deviceid;
	//1、ios, 2、安卓
	private int mode;
	//app版本号
	private String appVersion;
	//app类型：9、得脉：11、机遇
	private int app;
	//通知类型；详见Constants.MsgType
	private int type;
	//
	private int badge;
	//mq获取消息时间
	private long time;
	
	
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public int getBadge() {
		return badge;
	}
	public void setBadge(int badge) {
		this.badge = badge;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public int getApp() {
		return app;
	}
	public void setApp(int app) {
		this.app = app;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}