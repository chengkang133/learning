package com.kgdata.kafkatest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author CK
 * @Date 2023/3/8 14:27
 **/
@RestController
@RequestMapping("/msg")
public class MyKafkaController {
	private final static String TOPIC_NAME = "test";
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;


	@RequestMapping("/send")
	public String sendMessage() {
		kafkaTemplate.send(TOPIC_NAME, 0, "key", "this is a message!");
		return "send success!";
	}
}

