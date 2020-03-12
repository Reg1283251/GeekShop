package com.liam.shop.dao;

import java.sql.SQLException;
import java.util.List;

import com.liam.shop.pojo.Category;
import com.liam.shop.pojo.Product;

public interface IProductDao {
	/**
	 * -��ѯ������Ʒ
	 * @return 
	 * @throws SQLException
	 */
	List<Product> findHotProducts() throws SQLException;
	
	/**
	 * -��ѯ������Ʒ
	 * @return
	 * @throws SQLException
	 */
	List<Product> findNewProducts() throws SQLException;
	
	/**
	 * -��ѯ������Ʒ����
	 * @return
	 * @throws SQLException
	 */
	List<Category> findAllCategorys() throws SQLException;
	
	/**
	 * -��ѯ������Ʒ����
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	Category findCategoryByCid(String cid) throws SQLException;
	
	/**
	 * -���ݷ���ID��ѯ��������Ʒ����
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	int getCount(String cid) throws SQLException;
	
	/**
	 * -��ѯĳ�����µ���Ʒ�ķ�ҳ��Ϣ
	 * @param cid
	 * @param start
	 * @param count
	 * @return
	 * @throws SQLException
	 */
	List<Product> findProductListByPage(String cid, int start, int count) throws SQLException;
	
	/**
	 * -����PID������Ʒ��Ϣ
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	Product findProductByID(String pid) throws SQLException;
}
