package com.wangxin.rabbitmq.demo.rabbitmqdemo.config;

/**
 * @Author:jzwx
 * @Desicription: 定义数据交换方式枚举
 * @Date:Created in 2018-08-08 10:53
 * @Modified By:
 */
public enum RabbitMqExchangeEnum {

    EXCHANGE_DIRECT("EXCHANGE_DIRECT", "点对点"),

    EXCHANGE_TOPIC("EXCHANGE_TOPIC","消息订阅"),

    EXCHANGE_FANOUT("EXCHANGE_FANOUT","消息分发");

    private String code;

    private String desc;

    RabbitMqExchangeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
