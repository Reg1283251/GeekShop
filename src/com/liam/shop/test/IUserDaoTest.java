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
		
//		���ù������ȡuuid
		String uid = CommonUtils.getUUID();
		
//		ʵ����һ�������û�
		User user = new User(
					uid,"liam","1234","��ͯ",
					"tunglee0328@gmail.com","12313131321",new Date(),"��",0,CommonUtils.getUUID()
				);
		
//		ʵ����dao��ӿ�
		IUserDao dao = new IUserDaoImpl();
//		����dao�ȵ�insert��������������û�
		int result = dao.insert(user);
		
//		���Բ���sql�����������Ƿ����0
		TestCase.assertTrue(result>0);
		
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
