package com.zhengqing.demo.test.durablequeue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.zhengqing.demo.test.utils.RabbitMqUtils;

import java.util.Scanner;

public class Task01 {
	private static final String QUEUE_NAME = "durable-queue";

	public static void main(String[] args) throws Exception {
		try (Channel channel = RabbitMqUtils.getChannel();) {
			// 消息队列的持久化
			boolean durable = true;
			channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
			//从控制台当中接受信息
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNext()) {
				String message = scanner.next();
				// 消息的持久化
				channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
				System.out.println("发送消息完成:" + message);

				channel.basicQos(1);
			}
		}
	}
}