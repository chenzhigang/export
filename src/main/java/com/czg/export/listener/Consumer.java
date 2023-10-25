package com.czg.export.listener;

import com.alibaba.fastjson.JSON;
import com.czg.export.demo.vo.RocketMsg;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@RocketMQMessageListener(topic = "test_dead_msg", consumerGroup = "my-consumer-group")
public class Consumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(MessageExt message) {
        String body = new String(message.getBody(), UTF_8);
        RocketMsg rocketMsg = JSON.parseObject(body, RocketMsg.class);
        switch (rocketMsg.getTag()) {
            case "test_dead_msg_tag":
                System.out.println("消费者收到信息：===" + body);
                if (1 == 1) {
                    throw new RuntimeException("死信走起");
                }
                break;
            default:
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(3);
    }
}