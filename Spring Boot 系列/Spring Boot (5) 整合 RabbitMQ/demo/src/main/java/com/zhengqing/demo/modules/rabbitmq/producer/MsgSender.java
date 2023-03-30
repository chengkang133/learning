package com.zhengqing.demo.modules.rabbitmq.producer;

import com.zhengqing.demo.modules.rabbitmq.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p> 生产者 - 发送消息 </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/1/2 11:27
 */
@Slf4j
@Component
public class MsgSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {
		String msgContent = "Hello World ~";
		log.info("生产者发送消息 : " + msgContent);
		this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_KEY, msgContent);
	}

	public void sendDelayMsg(String message) {
		log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), message);
		rabbitTemplate.convertAndSend("X", "XA", "消息来自 ttl 为 10S 的队列: " + message);
		rabbitTemplate.convertAndSend("X", "XB", "消息来自 ttl 为 40S 的队列: " + message);
	}


	public void sendTTLMsg(String message, String ttlTime) {
		rabbitTemplate.convertAndSend("X", "XC", message, correlationData ->{
			correlationData.getMessageProperties().setExpiration(ttlTime);
			return correlationData;
		});
		log.info("当前时间：{},发送一条时长{}毫秒 TTL 信息给队列 C:{}", new Date(),ttlTime, message);
	}
}
