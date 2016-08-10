package com.demai.rabbitmq.shard;

/**
 * 节点基类
 */
public class Node {
    Long id;
    String exchangeName;//需唯一

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public Node(Long id, String exchangeName) {

        this.id = id;
        this.exchangeName = exchangeName;
    }


    @Override
    public String toString() {
        return this.id + "#" + this.exchangeName;
    }
}