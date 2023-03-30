package com.kgdata.kafkatest.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer {
	// concurrency就是同组下的消费者个数，就是并发消费数，建议小于等于分区总数
	@KafkaListener(
			groupId = "testGroup",
			topicPartitions = {
			@TopicPartition(topic = "test", partitions = {"0", "1"}),
			@TopicPartition(topic = "test_2", partitions = "0",
					partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))}, concurrency = "3")
	public void listenGroupPro(ConsumerRecord<String, String> record, Acknowledgment ack) {
		String value = record.value();
		System.out.println(value);
		System.out.println(record);
		//手动提交offset
		ack.acknowledge();
	}
}