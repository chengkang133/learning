package com.kgdata.mysqltest.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.util.CollectionUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: lzyCug
 * @date: 2021/3/8 15:24
 * @description: 类型转换器，用于数据库的varchar和Java中Set<String>类型的相互转换
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class SetToVarcharTypeHandler implements TypeHandler<Set<String>> {
	@Override
	public void setParameter(PreparedStatement preparedStatement, int i, Set<String> strings, JdbcType jdbcType) throws SQLException {
		// 遍历set类型的入参，拼装为String类型，使用Statement对象插入数据库
		if (CollectionUtils.isEmpty(strings)) {
			return;
		}
		String joinStr = StringUtils.join(strings, ",");
		preparedStatement.setString(i, joinStr);
	}

	@Override
	public Set<String> getResult(ResultSet resultSet, String s) throws SQLException {
		// 获取String类型的结果，使用","分割为List后返回
		String resultString = resultSet.getString(s);
		if (StringUtils.isNotEmpty(resultString)) {
			return new HashSet<>(Arrays.asList(resultString.split(",")));
		}
		return null;
	}

	@Override
	public Set<String> getResult(ResultSet resultSet, int i) throws SQLException {
		// 获取String类型的结果，使用","分割为List后返回
		String resultString = resultSet.getString(i);
		if (StringUtils.isNotEmpty(resultString)) {
			return new HashSet<>(Arrays.asList(resultString.split(",")));
		}
		return null;
	}

	@Override
	public Set<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
		// 获取String类型的结果，使用","分割为List后返回
		String resultString = callableStatement.getString(i);
		if (StringUtils.isNotEmpty(resultString)) {
			return new HashSet<>(Arrays.asList(resultString.split(",")));
		}
		return null;
	}
}
