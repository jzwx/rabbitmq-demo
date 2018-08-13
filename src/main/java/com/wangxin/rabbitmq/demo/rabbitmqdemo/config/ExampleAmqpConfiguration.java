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
 * @Date:Created in 2018-08-08 13:53
 * @Modified By:
 */
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class ExampleAmqpConfiguration {

    @Bean("testQueueContainer")
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMqQueueNameEnum.TESTQUEUE.getCode());
        container.setMessageListener(exampleListener());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }


    @Bean("testQueueListener")
    public ChannelAwareMessageListener exampleListener() {
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                TestUser testUser = (TestUser) SerializeUtil.unserialize(message.getBody());
//                通过设置TestUser的name来测试回调，分别发两条消息，一条UserName为1，一条为2，查看控制台中队列中消息是否被消费
//                if ("2".equals(testUser.getUserName())) {
                    System.out.println(testUser.toString());
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//                }
//                if ("1".equals(testUser.getUserName())) {
//                    System.out.println(testUser.toString());
////                channel.basicNack拒绝消息重新排队
//                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//                }
            }
        };
    }
}
