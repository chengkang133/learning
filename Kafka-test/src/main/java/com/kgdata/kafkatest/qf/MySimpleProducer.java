package com.kgdata.kafkatest.qf;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author CK
 * @Date 2023/3/8 13:46
 **/
public class MySimpleProducer {
	private final static String TOPIC_NAME = "my-replicated-topic";

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		//1.设置参数
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.253.135:9092");
		//把发送的key从字符串序列化为字节数组
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		//把发送消息value从字符串序列化为字节数组
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		//2.创建⽣产消息的客户端，传⼊参数
		Producer<String, String> producer = new KafkaProducer<>(props);
		//3.创建消息
		//key：作⽤是决定了往哪个分区上发，value：具体要发送的消息内容
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, "mykey", "hellokafka");
		//4.同步发送消息,得到消息发送的元数据并输出
//		RecordMetadata metadata = producer.send(producerRecord).get();
//		System.out.println("同步⽅式发送消息结果：" + "topic-" + metadata.topic() + "|partition-" + metadata.partition() + "|offset-" + metadata.offset());

		//5.异步发送消息
		producer.send(producerRecord, (metadata1, exception) -> {
			if (exception != null) {
				System.err.println("发送消息失败：" + exception.getStackTrace());
			}
			if (metadata1 != null) {
				System.out.println("异步方式发送消息结果：" + "topic-" + metadata1.topic() + "|partition-" + metadata1.partition() + "|offset-" + metadata1.offset());
			}
		});
	}
}
