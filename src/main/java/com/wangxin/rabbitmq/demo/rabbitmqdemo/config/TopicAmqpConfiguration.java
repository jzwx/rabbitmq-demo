package com.wangxin.rabbitmq.demo.rabbitmqdemo.config;

import com.rabbitmq.client.Channel;
import com.wangxin.rabbitmq.demo.rabbitmqdemo.domain.TestUser;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:jzwx
 * @Desicription: 消费者配置
 * @Date:Created in 2018-08-08 13:46
 * @Modified By:
 */
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class TopicAmqpConfiguration {
    @Bean("topicTest1Container")
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMqQueueNameEnum.TOPICTEST1.getCode());
        container.setMessageListener(exampleListener1());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }


    @Bean("topicTest1Listener")
    public ChannelAwareMessageListener exampleListener1(){
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                TestUser testUser = (TestUser) SerializeUtil.unserialize(message.getBody());
                System.out.println("TOPICTEST1："+testUser.toString());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

            }
        };
    }
}
