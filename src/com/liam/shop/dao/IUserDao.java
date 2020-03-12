package com.liam.shop.dao;

import java.sql.SQLException;

import com.liam.shop.pojo.User;

public interface IUserDao {
	
	/**
	 * -插入用户
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int insert(User user) throws SQLException;
	
	/**
	 * -激活用户
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public int active(String code) throws SQLException;
	
	/**
	 * -根据用户名查找用户
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public User findByUsername(String username) throws SQLException;

}
