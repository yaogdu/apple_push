package com.demai.rabbitmq.vo;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MsgObject {

    private int type;//类型: 1 广播，0 单播给指定target
    private Map<String, Object> target;

    private int contentType;//content的数据格式标示
    private long toId;

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, Object> getTarget() {
        return target;
    }

    public void setTarget(Map<String, Object> target) {
        this.target = target;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }

    public static void main(String[] args) {
        MsgObject o = new MsgObject();
        Map<String, Object> target = new HashMap<String, Object>();
        target.put("id", 10);
        target.put("type", 1);
        o.setType(0);
        o.setTarget(target);

        System.out.println(o);
    }

}
