package com.demai.common;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.demai.entity.Msg;

/**
 * 缓存消息列表
 * 
 * @author qianchun
 * @date 2016年5月20日 下午4:07:51
 */
@Component
public class NoticeListCache {
	private LinkedList<Msg> joinMsgList;

	@SuppressWarnings({"unchecked", "rawtypes"})
	public synchronized List<Msg> get() {
		LinkedList<Msg> tmp = null;
		if(joinMsgList==null) {
			joinMsgList = new LinkedList<>();
			tmp = joinMsgList;
		} else {
			tmp = (LinkedList) joinMsgList.clone();
			joinMsgList.clear();
		}
		return tmp;
	}

	public synchronized void put(Msg msg) {
		if(joinMsgList==null) {
			joinMsgList = new LinkedList<>();
		}
		joinMsgList.add(msg);
	}
	public static void main(String[] args) {
		NoticeListCache n = new NoticeListCache();
		for(int i=0; i<10 ; i++) {
			Msg msg = new Msg();
			msg.setFeedId(i);
			n.put(msg);
		}
		System.out.println(n.joinMsgList);
		n.get();
		System.out.println(n.joinMsgList);
	}
}
