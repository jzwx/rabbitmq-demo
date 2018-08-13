package com.wangxin.rabbitmq.demo.rabbitmqdemo.controller;

import com.wangxin.rabbitmq.demo.rabbitmqdemo.config.RabbitMqExchangeEnum;
import com.wangxin.rabbitmq.demo.rabbitmqdemo.config.RabbitMqRoutingKeyEnum;
import com.wangxin.rabbitmq.demo.rabbitmqdemo.config.RabbitMqSender;
import com.wangxin.rabbitmq.demo.rabbitmqdemo.domain.TestUser;
import com.wangxin.rabbitmq.demo.rabbitmqdemo.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;

/**
 * @Author:jzwx
 * @Desicription: RabbitMq测试控制器
 * @Date:Created in 2018-08-08 13:57
 * @Modified By:
 */
@Controller
@RequestMapping("/rabbitMq")
public class TestRabbitMqController {

    public static final Logger logger = LoggerFactory.getLogger(TestRabbitMqController.class);

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/testInfo", method = RequestMethod.GET)
    public @ResponseBody
    String testRabbitMq() {
        TestUser testUser = new TestUser();
        testUser.setUserName("1");
        testUser.setAge("10");
        testUser.setName("三大");
        testUser.setPhone("12345678912");
        testUser.setSex("男");
        rabbitMqSender.sendRabbitmqDirect(RabbitMqRoutingKeyEnum.TESTQUEUE.getCode(), testUser);

        rabbitMqSender.sendRabbitmqTopic("lazy.1.2", testUser);
        rabbitMqSender.sendRabbitmqTopic("lazy.TEST.2", testUser);

        TestUser testUser2 = new TestUser();
        testUser2.setUserName("2");
        testUser2.setAge("100");
        testUser2.setName("三大长度");
        testUser2.setPhone("13568656321");
        testUser2.setSex("女");
        rabbitMqSender.sendRabbitmqDirect(RabbitMqRoutingKeyEnum.TESTQUEUE.getCode(), testUser2);

//        rabbitMqSender.sendRabbitmqTopic("lazy.1.2",testUser);
//        rabbitMqSender.sendRabbitmqTopic("lazy.TEST.2",testUser);
        return "ok";
    }

    @RequestMapping(value = "/yanShiQueue", method = RequestMethod.GET)
    public @ResponseBody
    String testQueue() {
        TestUser testUser = new TestUser();
        testUser.setUserName("1");
        testUser.setAge("10");
        testUser.setName("三大");
        testUser.setPhone("12345678912");
        testUser.setSex("男");
        testUser.setTime(new Date());
        messageService.sendMsg(testUser,
                RabbitMqExchangeEnum.EXCHANGE_DIRECT.getCode(),
                RabbitMqRoutingKeyEnum.MESSAGE_TTL_QUEUE.getCode(),
                10000);
        return "hello world!";
    }

    @RequestMapping(value = "/sendTrans")
    public @ResponseBody
    String send() {
        TestUser testUser = new TestUser();
        testUser.setUserName("1");
        testUser.setAge("10");
        testUser.setName("三大");
        testUser.setPhone("12345678912");
        testUser.setSex("男");
        testUser.setTime(new Date());
        messageService.sendTransMsg(testUser, RabbitMqExchangeEnum.EXCHANGE_TOPIC.getCode(), RabbitMqRoutingKeyEnum.MESSAGE_TRANS_QUEUE.getCode());
        return "hehe";
    }
}
