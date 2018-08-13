package com.wangxin.rabbitmq.demo.rabbitmqdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @Author:jzwx
 * @Desicription: rabbitmq发送消息工具类
 * @Date:Created in 2018-08-08 13:32
 * @Modified By:
 */
@Component
public class RabbitMqSender implements RabbitTemplate.ConfirmCallback {
    /**
     * 日志打印器
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);

    /**
     * rabbitmq模板
     */
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 直连 发送到 指定routekey的指定queue
     *
     * @param routeKey
     * @param obj
     */
    public void sendRabbitmqDirect(String routeKey, Object obj) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        logger.info("send: " + correlationData.getId());
        this.rabbitTemplate.convertAndSend(RabbitMqExchangeEnum.EXCHANGE_DIRECT.getCode(), routeKey, obj, correlationData);
    }

    /**
     * 所有发送到Topic Exchange的消息被转发到所有关心RouteKey中指定Topic的Queue上
     * @param routeKey
     * @param obj
     */
    public void sendRabbitmqTopic(String routeKey, Object obj) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        logger.info("send: " + correlationData.getId());
        this.rabbitTemplate.convertAndSend(RabbitMqExchangeEnum.EXCHANGE_TOPIC.getCode(), routeKey, obj, correlationData);
    }

    /**
     * 发布消息后进行回调
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" 回调id:" + correlationData.getId());
        if (ack) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败:" + cause);
        }
    }
}
