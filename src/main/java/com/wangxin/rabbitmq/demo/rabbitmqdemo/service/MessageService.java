package com.wangxin.rabbitmq.demo.rabbitmqdemo.service;

/**
 * @Author:jzwx
 * @Desicription: MessageService
 * @Date:Created in 2018-08-09 10:29
 * @Modified By:
 */
public interface MessageService {
    void sendMsg(Object messageContent, String exchange, String routerKey, final long delayTimes);

    void sendTransMsg(Object messageContent, String exchange, String routerKey);
}
