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
 * @Date:Created in 2018-08-08 13:56
 * @Modified By:
 */
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class TopicAmqpConfiguration2 {
    @Bean("topicTest2Container")
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMqQueueNameEnum.TOPICTEST2.getCode());
        container.setMessageListener(exampleListener());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }


    @Bean("topicTest2Listener")
    public ChannelAwareMessageListener exampleListener() {
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                TestUser testUser = (TestUser) SerializeUtil.unserialize(message.getBody());
                System.out.println("TOPICTEST2：" + testUser.toString());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            }
        };
    }
}
