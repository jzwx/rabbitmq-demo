package com.wangxin.rabbitmq.demo.rabbitmqdemo.config;

/**
 * @Author:jzwx
 * @Desicription: 队列名称枚举
 * @Date:Created in 2018-08-08 11:04
 * @Modified By:
 */
public enum RabbitMqQueueNameEnum {
    TESTQUEUE("TESTQUEUE", "测试队列"),

    MESSAGE_QUEUE("message.center.create","MESSAGE_QUEUE队列名称"),

    MESSAGE_TTL_QUEUE("message.center.create.ttl","MESSAGE_TTL_QUEUE队列名称"),

    MESSAGE_TRANS_QUEUE("trans","MESSAGE_TRANS_QUEUE队列名称"),

    TOPICTEST1("TOPICTEST1", "topic测试队列"),

    TOPICTEST2("TOPICTEST2", "topic测试队列");

    RabbitMqQueueNameEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
