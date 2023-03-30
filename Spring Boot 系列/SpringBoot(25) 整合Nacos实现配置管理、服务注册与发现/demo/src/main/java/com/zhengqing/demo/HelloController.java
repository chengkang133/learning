package com.zhengqing.demo;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 动态加载配置测试Controller </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/3/30 21:34
 */
@RestController
public class HelloController {

    @NacosValue(value = "${helloworld:HelloWorld}", autoRefreshed = true)
    private String hello;

    @GetMapping("/hello")
    public String hello() {
        return hello;
    }

}
