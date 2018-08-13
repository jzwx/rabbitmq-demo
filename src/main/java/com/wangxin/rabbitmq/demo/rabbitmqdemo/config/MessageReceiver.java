package com.wangxin.rabbitmq.demo.rabbitmqdemo.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:jzwx
 * @Desicription: 延时队列消费者
 * @Date:Created in 2018-08-09 10:31
 * @Modified By:
 */
public class MessageReceiver {
    public void receive(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("消息接收时间:"+sdf.format(new Date()));
        System.out.println("接收到的消息:"+msg);
    }
}
