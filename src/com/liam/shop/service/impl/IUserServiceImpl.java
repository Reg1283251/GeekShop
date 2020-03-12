package com.liam.shop.service.impl;

import java.sql.SQLException;

import com.liam.shop.dao.IUserDao;
import com.liam.shop.dao.impl.IUserDaoImpl;
import com.liam.shop.pojo.User;
import com.liam.shop.service.UserService;

public class IUserServiceImpl implements UserService {

	@Override
	public int register(User user) {
		IUserDao dao = new IUserDaoImpl();
		int result = 0;
		try {
			result = dao.insert(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int active(String code) {
		IUserDao dao = new IUserDaoImpl();
		int result = 0;
		try {
			result = dao.active(code);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean findByUsername(String username) {
		IUserDao dao = new IUserDaoImpl();
		User user = null;
		try {
			user = dao.findByUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user==null;
	}
	

}
