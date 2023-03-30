package com.zhengqing.demo.modules.rabbitmq.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description 在当消息传递过程中不可达目的地时将消息返回给生产者
 * @Author CK
 * @Date 2023/3/22 9:25
 **/
@Component
@Slf4j
public class MyReturnCallback implements RabbitTemplate.ReturnCallback {
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		log.info("消息:{} 被服务器退回，退回原因:{}, 交换机是:{}, 路由 key:{}",
				new String(message.getBody()), replyText, exchange, routingKey);
	}
}
