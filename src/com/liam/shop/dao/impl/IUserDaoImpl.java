package com.liam.shop.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.liam.shop.dao.IUserDao;
import com.liam.shop.pojo.User;
import com.liam.utils.DataSourceUtils;

public class IUserDaoImpl implements IUserDao{

	@Override
	public int insert(User user) throws SQLException {
//		利用核心类获取数据源
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		
//		要执行的sql语句
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		
//		设置参数
		Object[] params = {
				user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),
				user.getCode()};
		
//		利用核心类对象执行sql
		int result = runner.update(sql, params);
		
		return result;
	}

	@Override
	public int active(String code) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state = 1 where code = ?";
		Object[] params = {code};
		int result = runner.update(sql,params);
		return result;
	}

	@Override
	public User findByUsername(String username) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ?";
		
		User user = runner.query(sql,new BeanHandler<>(User.class),username);
		return user;
	}
	
	

}
