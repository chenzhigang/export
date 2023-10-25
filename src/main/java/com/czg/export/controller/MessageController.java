package com.czg.export.controller;

import com.czg.export.demo.vo.RocketMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class MessageController {
 
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private DefaultMQProducer producer;

    @GetMapping(value = "/createProduct2")
    public String  createProduct2(String topic, String payload) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        log.info("topic:{},content:{}", topic, payload);
        // 添加信息
        RocketMsg rocketMsg = new RocketMsg();
        rocketMsg.setTag(topic + "_tag");
        rocketMsg.setTopic(topic);
        rocketMsg.setPayload(payload);
        rocketMsg.setMsgKey(Long.toString(System.currentTimeMillis()));
        Message message = new Message(topic, rocketMsg.getTag(), payload.getBytes(StandardCharsets.UTF_8));
        SendResult sendResult = producer.send(message);
        log.info("发送结果：{}", sendResult);
        return "添加成功";
    }

    @GetMapping(value = "/createProduct")
    public String  createProduct(String topic, String payload) {
        log.info("topic:{},content:{}", topic, payload);
        // 添加信息
        RocketMsg rocketMsg = new RocketMsg();
        rocketMsg.setTag(topic + "_tag");
        rocketMsg.setTopic(topic);
        rocketMsg.setPayload(payload);
        rocketMsg.setMsgKey(Long.toString(System.currentTimeMillis()));
        org.springframework.messaging.Message message = MessageBuilder.withPayload(rocketMsg)
                .setHeader(RocketMQHeaders.KEYS, rocketMsg.getMsgKey()).build();
        rocketMQTemplate.asyncSend(topic + ":" + rocketMsg.getTag(), message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("成功了");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("失败了");
            }
        });
        return "添加成功";
    }

}