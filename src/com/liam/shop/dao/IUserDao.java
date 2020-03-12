package com.liam.shop.dao;

import java.sql.SQLException;

import com.liam.shop.pojo.User;

public interface IUserDao {
	
	/**
	 * -�����û�
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int insert(User user) throws SQLException;
	
	/**
	 * -�����û�
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public int active(String code) throws SQLException;
	
	/**
	 * -�����û��������û�
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public User findByUsername(String username) throws SQLException;

}
