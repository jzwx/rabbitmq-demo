package com.wangxin.rabbitmq.demo.rabbitmqdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:jzwx
 * @Desicription: 用于配置交换机和队列对应关系(生产者配置)
 * @Date:Created in 2018-08-08 11:12
 * @Modified By:
 */
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class RabbitMqExchangeConfig {

    /**
     * 日志打印器
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqExchangeConfig.class);

    /**
     * topic主题交换机实例化
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public TopicExchange contractTopicExchangeDurable(RabbitAdmin rabbitAdmin) {
        TopicExchange contractTopicExchange = new TopicExchange(RabbitMqExchangeEnum.EXCHANGE_TOPIC.getCode(), true, false);
        rabbitAdmin.declareExchange(contractTopicExchange);
        logger.info("完成主题(topic)型交换机bean实例化");
        return contractTopicExchange;
    }

    /**
     * direct直连交换机实例化
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public DirectExchange contractDirectExchange(RabbitAdmin rabbitAdmin) {
        DirectExchange contractDirectExchange = new DirectExchange(RabbitMqExchangeEnum.EXCHANGE_DIRECT.getCode(), true, false);
        rabbitAdmin.declareExchange(contractDirectExchange);
        logger.info("完成直连型交换机bean实例化");
        return contractDirectExchange;
    }

    /**
     * 事物队列
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Queue transQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqQueueNameEnum.MESSAGE_TRANS_QUEUE.getCode(), true, false, true, null);
        rabbitAdmin.declareQueue(queue);
        logger.info("事物队列实例化完成");
        return queue;
    }

    /**
     * 事物队列绑定
     *
     * @param transQueue  消息中心队列
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Binding transBinding(Queue transQueue, RabbitAdmin rabbitAdmin) {
        Binding binding = new Binding(RabbitMqQueueNameEnum.MESSAGE_TRANS_QUEUE.getCode(), Binding.DestinationType.QUEUE, RabbitMqExchangeEnum.EXCHANGE_TOPIC.getCode(), RabbitMqRoutingKeyEnum.MESSAGE_TRANS_QUEUE.getCode(), null);
        rabbitAdmin.declareBinding(binding);
        logger.info("事物绑定");
        return binding;
    }

    /**
     * 延时队列
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Queue messageQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqQueueNameEnum.MESSAGE_QUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.info("延时队列实例化完成");
        return queue;
    }

    /**
     * 转发延时队列
     *
     * @return
     */
    @Bean
    public Queue messageTtlQueue(RabbitAdmin rabbitAdmin) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", RabbitMqExchangeEnum.EXCHANGE_DIRECT.getCode());
        arguments.put("x-dead-letter-routing-key", RabbitMqRoutingKeyEnum.MESSAGE_QUEUE.getCode());
        Queue queue = new Queue(RabbitMqQueueNameEnum.MESSAGE_TTL_QUEUE.getCode(), true, false, false, arguments);
        rabbitAdmin.declareQueue(queue);
        logger.info("转发延时队列实例化完成");
        return queue;
    }

    /**
     * 消息中心实际消息交换与队列绑定
     *
     * @param exchange     消息中心交换配置
     * @param messageQueue 消息中心队列
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Binding messageBinding(Queue messageQueue, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(messageQueue).to(exchange).with(RabbitMqRoutingKeyEnum.MESSAGE_QUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.info("延时绑定");
        return binding;
    }

    /**
     * 消息中心TTL绑定实际消息中心实际消费交换机
     *
     * @param messageTtlQueue
     * @param exchange
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Binding messageTtlBinding(Queue messageTtlQueue, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(messageTtlQueue).to(exchange).with(RabbitMqRoutingKeyEnum.MESSAGE_TTL_QUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.info("转发延时绑定");
        return binding;
    }

    /**
     * 直连测试队列
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Queue queueTest(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqQueueNameEnum.TESTQUEUE.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.info("测试队列实例化完成");
        return queue;
    }

    /**
     * topic 1 队列
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Queue queueTopicTest1(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqQueueNameEnum.TOPICTEST1.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.info("话题测试队列1实例化完成");
        return queue;
    }

    /**
     * topic 2队列
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Queue queueTopicTest2(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitMqQueueNameEnum.TOPICTEST2.getCode());
        rabbitAdmin.declareQueue(queue);
        logger.info("话题测试队列2实例化完成");
        return queue;
    }

    /**
     * 直连测试队列和direct交换机绑定
     *
     * @param queueTest
     * @param exchange
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Binding bindingQueueTest(Queue queueTest, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueTest).to(exchange).with(RabbitMqRoutingKeyEnum.TESTQUEUE.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.info("测试队列与直连型交换机绑定完成");
        return binding;
    }

    /**
     * topic1队列和topic(话题)交换机绑定
     *
     * @param queueTopicTest1
     * @param exchange
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Binding bindingQueueTopicTest1(Queue queueTopicTest1, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueTopicTest1).to(exchange).with(RabbitMqRoutingKeyEnum.TESTTOPICQUEUE1.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.info("测试队列与话题交换机1绑定完成");
        return binding;
    }

    /**
     * topic2队列和topic(话题)交换机绑定
     *
     * @param queueTopicTest2
     * @param exchange
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Binding bindingQueueTopicTest2(Queue queueTopicTest2, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueTopicTest2).to(exchange).with(RabbitMqRoutingKeyEnum.TESTTOPICQUEUE2.getCode());
        rabbitAdmin.declareBinding(binding);
        logger.info("测试队列与话题交换机2绑定完成");
        return binding;
    }

}
