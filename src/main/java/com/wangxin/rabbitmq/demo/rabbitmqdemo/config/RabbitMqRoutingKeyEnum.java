package com.wangxin.rabbitmq.demo.rabbitmqdemo.config;

/**
 * @Author:jzwx
 * @Desicription: 自定义匹配路由routing_key
 * @Date:Created in 2018-08-08 11:07
 * @Modified By:
 */
public enum RabbitMqRoutingKeyEnum {
    TESTQUEUE("TESTQUEUE1", "测试队列key"),

    MESSAGE_QUEUE("message.center.create","MESSAGE_QUEUE队列名称"),

    MESSAGE_TTL_QUEUE("message.center.create.ttl","MESSAGE_TTL_QUEUE队列名称"),

    MESSAGE_TRANS_QUEUE("trans","MESSAGE_TRANS_QUEUE队列名称"),

    TESTTOPICQUEUE1("*.TEST.*", "topic测试队列key"),

    TESTTOPICQUEUE2("lazy.#", "topic测试队列key");

    RabbitMqRoutingKeyEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
