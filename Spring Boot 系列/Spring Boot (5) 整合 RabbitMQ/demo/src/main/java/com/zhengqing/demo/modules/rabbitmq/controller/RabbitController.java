package com.zhengqing.demo.modules.rabbitmq.controller;

import com.zhengqing.demo.modules.common.dto.output.ApiResult;
import com.zhengqing.demo.modules.rabbitmq.producer.MsgSender;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class RabbitController {

	@Autowired
	private MsgSender msgSender;

	@GetMapping(value = "/sendMsg", produces = "application/json;charset=utf-8")
	@ApiOperation(value = "发送消息", httpMethod = "GET", response = ApiResult.class)
	public ApiResult sendMsg() {
		msgSender.send();
		return ApiResult.ok();
	}

	@GetMapping("ttl/sendMsg/{message}")
	public ApiResult sendMsg(@PathVariable String message) {
		msgSender.sendDelayMsg(message);
		return ApiResult.ok();
	}

	@GetMapping("ttl/sendMsg/{message}/{ttlTime}")
	public ApiResult sendTTLMsg(@PathVariable String message, @PathVariable String ttlTime) {
		msgSender.sendTTLMsg(message, ttlTime);
		return ApiResult.ok();
	}

}
