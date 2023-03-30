package com.zhengqing.demo.modules.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.zhengqing.demo.modules.rabbitmq.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.zhengqing.demo.modules.rabbitmq.config.RabbitConfig.DEAD_LETTER_QUEUE;

/**
 * <p> 消费者 - 接收消息 </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/1/2 11:28
 */
@Slf4j
@Component

public class MsgReceiver {

	@RabbitHandler
	@RabbitListener(queues = RabbitConfig.QUEUE_KEY)
	public void process(String msg) {
		log.info("消费者接收消息 : " + msg);
	}


	@RabbitListener(queues = DEAD_LETTER_QUEUE)
	public void receiveD(Message message, Channel channel) {
		String msg = new String(message.getBody());
		log.info("当前时间：{},收到死信队列信息: {}", new Date().toString(), msg);
	}

}
