package com.liam.shop.test;

import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.liam.shop.dao.IUserDao;
import com.liam.shop.dao.impl.IUserDaoImpl;
import com.liam.shop.pojo.User;
import com.liam.utils.CommonUtils;

import junit.framework.TestCase;

class IUserDaoTest {

	@Test
	void testInsert() throws SQLException {

//		调用工具类获取uuid
		String uid = CommonUtils.getUUID();

//		实例化一个测试用户
		User user = new User(
				uid, "liam", "1234", "李童",
				"tunglee0328@gmail.com", "12313131321", new Date(), "男", 0, CommonUtils.getUUID()
		);

//		实例化dao层接口
		IUserDao dao = new IUserDaoImpl();
//		测试dao等的insert方法，传入测试用户
		int result = dao.insert(user);

//		断言测试sql语句操作行数是否大于0
		TestCase.assertTrue(result > 0);

	}
	
	@Test
	void testFindByUsername() throws SQLException{
		String username = "test";
		IUserDao dao = new IUserDaoImpl();
		User user = dao.findByUsername(username);
		System.out.println(user);
		TestCase.assertNotNull(user);
	}

}
