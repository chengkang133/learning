package com.kgdata.mysqltest.pojo;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author: lzyCug
 * @date: 2021/3/8 11:15
 * @description: 用户实体类
 */
@Data
public class User {
	private Integer id;
	private String name;
	private Integer age;
	private String address;
	// 用户的爱好信息使用List集合来存储
	private List<String> hobbies;
	private Set<String> cars;
}
