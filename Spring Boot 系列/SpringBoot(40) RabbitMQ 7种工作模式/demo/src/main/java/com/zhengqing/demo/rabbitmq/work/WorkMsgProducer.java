package com.zhengqing.demo.rabbitmq.work;

import com.zhengqing.demo.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkMsgProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String msgContent = "Hello World";
        log.info("[生产者] 发送消息: {}", msgContent);
        this.rabbitTemplate.convertAndSend(MqConstant.WORK_QUEUE, msgContent);
    }

}
