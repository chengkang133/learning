package com.zhengqing.demo.test.workqueues;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zhengqing.demo.test.utils.RabbitMqUtils;
import com.zhengqing.demo.test.utils.SleepUtils;

public class Worker03 {
	private static final String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {
		Channel channel = RabbitMqUtils.getChannel();
		System.out.println("C3 等待接收消息处理时间较短");
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String receivedMessage = new String(delivery.getBody());
			SleepUtils.sleep(1);
			System.out.println("接收到消息:" + receivedMessage);
			/**
			 * 1.消息标记 tag
			 * 2.是否批量应答未应答消息
			 */
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		};
		CancelCallback cancelCallback = (consumerTag) -> {
			System.out.println(consumerTag + "消费者取消消费接口回调逻辑");
		};
		System.out.println("C3 消费者启动等待消费......");
		// 手动应答
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
	}
}