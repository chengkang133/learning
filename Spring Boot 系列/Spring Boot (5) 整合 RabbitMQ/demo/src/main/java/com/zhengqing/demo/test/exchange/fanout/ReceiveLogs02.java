package com.zhengqing.demo.test.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zhengqing.demo.test.utils.RabbitMqUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class ReceiveLogs02 {
	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {
		Channel channel = RabbitMqUtils.getChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		/**
		 * 生成一个临时的队列 队列的名称是随机的
		 * 当消费者断开和该队列的连接时 队列自动删除
		 */
		String queueName = channel.queueDeclare().getQueue();
		//把该临时队列绑定我们的 exchange 其中 routingkey(也称之为 binding key)为空字符串
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		System.out.println("等待接收消息,把接收到的消息写到文件.....");
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			File file = new File("E:\\test\\rabbitmq_info.txt");
			FileUtils.writeStringToFile(file, message, "UTF-8");
			System.out.println("数据写入文件成功");
		};
		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
	}
}