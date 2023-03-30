package com.atguigu.mybatis_plus.mapper;

import com.atguigu.mybatis_plus.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}