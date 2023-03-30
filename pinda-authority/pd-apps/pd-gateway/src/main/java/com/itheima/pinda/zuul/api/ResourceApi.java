package com.itheima.pinda.zuul.api;

import com.itheima.pinda.authority.dto.auth.ResourceQueryDTO;
import com.itheima.pinda.authority.entity.auth.Resource;
import com.itheima.pinda.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 在网关服务中会通过Feign来调用权限服务获取相关信息，所以需要定义API接口和对应的熔断器类
 */
@FeignClient(name = "${pinda.feign.authority-server:pd-auth-server}",
   fallback = ResourceApiFallback.class)
public interface ResourceApi {
  //获取所有需要鉴权的资源
  @GetMapping("/resource/list")
  R<List> list();

  //查询当前登录用户拥有的资源权限
  @GetMapping("/resource")
  R<List<Resource>> visible(ResourceQueryDTO resource);
}
