package com.wangxin.rabbitmq.demo.rabbitmqdemo.config;

import com.rabbitmq.client.Channel;
import com.wangxin.rabbitmq.demo.rabbitmqdemo.domain.TestUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Desicription: 延时消费者配置
 * @Date:Created in 2018-08-08 13:53
 * @Modified By:
 */
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class ExampleAmqpConfiguration2 {

    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ExampleAmqpConfiguration2.class);

    @Bean("testQueueContainer2")
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMqQueueNameEnum.MESSAGE_QUEUE.getCode());
        container.setMessageListener(exampleListener());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }


    @Bean("testQueueListener2")
    public ChannelAwareMessageListener exampleListener() {
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                TestUser testUser = (TestUser) SerializeUtil.unserialize(message.getBody());
                logger.info("消费内容：{}", testUser);
                System.out.println(testUser.toString());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        };
    }
}
