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
//		���ú������ȡ����Դ
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		
//		Ҫִ�е�sql���
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		
//		���ò���
		Object[] params = {
				user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),
				user.getCode()};
		
//		���ú��������ִ��sql
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
