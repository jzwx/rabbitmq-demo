package com.wangxin.rabbitmq.demo.rabbitmqdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.wangxin.rabbitmq.demo.rabbitmqdemo.config.RabbitMqExchangeEnum;
import com.wangxin.rabbitmq.demo.rabbitmqdemo.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:jzwx
 * @Desicription: MessageServiceImpl
 * @Date:Created in 2018-08-09 10:30
 * @Modified By:
 */
@Service
public class MessageServiceImpl implements MessageService{
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    /**
     * RabbitMQ 模版消息实现类
     */
    @Autowired
    private AmqpTemplate rabbitMqTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(Object messageContent, String exchange, String routerKey, final long delayTimes) {
        if (!StringUtils.isEmpty(exchange)) {
            logger.info("延迟：{}毫秒写入消息队列：{}，消息内容：{}", delayTimes, routerKey, JSON.toJSONString(messageContent));
            // 执行发送消息到指定队列
            rabbitTemplate.convertAndSend(exchange, routerKey, messageContent, message -> {
                // 设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            });
        } else {
            logger.error("未找到队列消息：{}，所属的交换机", exchange);
        }
    }

    @Override
//    @Transactional
    public void sendTransMsg(Object messageContent, String exchange, String routerKey) {
        rabbitTemplate.convertAndSend(exchange, routerKey, messageContent);
        if(exchange.equals(RabbitMqExchangeEnum.EXCHANGE_TOPIC.getCode())){
            throw new RuntimeException("error!");
        }
        logger.info("出错啦！");
    }
}
