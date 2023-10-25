package com.czg.export.listener;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "test_dead_msg_todo", consumerGroup = "my-consumer-group-todo")
public class Consumer2 implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(String message) {
        System.out.println("消费者收到信息：===" + message);
        if (1 == 1) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
    }
}