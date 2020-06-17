package com.liam.shop.test;

import com.liam.shop.dao.IProductDao;
import com.liam.shop.dao.impl.IProductDaoImpl;
import com.liam.shop.pojo.Category;
import com.liam.shop.pojo.Product;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

class IProductDaoTest {

//	dao层测试类

	@Test
	void testFindHotProducts() throws SQLException {
		IProductDao dao = new IProductDaoImpl();
		List<Product> products = dao.findHotProducts();
		TestCase.assertNotNull(products);
		for (Product product : products) {
			System.out.println(product.toString());
		}
	}

	@Test
	void testFindNewProducts() throws SQLException {
		IProductDao dao = new IProductDaoImpl();
		List<Product> products = dao.findNewProducts();
		TestCase.assertNotNull(products);
		for (Product product : products) {
			System.out.println(product.toString());
		}
	}

	@Test
	void testFindAllCategorys() throws SQLException {
		IProductDao dao = new IProductDaoImpl();
		List<Category> categorys = dao.findAllCategorys();
		TestCase.assertNotNull(categorys);
	}

	@Test
	void testGetCount() throws SQLException {
		IProductDao dao = new IProductDaoImpl();
		int result = dao.getCount("1");
		TestCase.assertTrue(result > 0);
		System.out.println(result);
	}

	@Test
	void testFindProductListByPage() throws SQLException {
		IProductDao dao = new IProductDaoImpl();
		List<Product> result = dao.findProductListByPage("1", 0, 10);
		TestCase.assertNotNull(result);
		
		for (Product product : result) {
			System.out.println(product);
		}
	}
	
	@Test
	void testFindProductByID() throws SQLException {
		IProductDao dao = new IProductDaoImpl();
		Product p = dao.findProductByID("1");
		TestCase.assertNotNull(p);
		
		System.out.println(p);
		
	}

}
