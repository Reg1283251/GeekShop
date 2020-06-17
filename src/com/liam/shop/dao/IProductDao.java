package com.liam.shop.dao;

import java.sql.SQLException;
import java.util.List;

import com.liam.shop.pojo.Category;
import com.liam.shop.pojo.Product;

public interface IProductDao {
	/**
	 * -查询最热商品
	 *
	 * @return
	 * @throws SQLException
	 */
	List<Product> findHotProducts() throws SQLException;

	/**
	 * -查询最新商品
	 *
	 * @return
	 * @throws SQLException
	 */
	List<Product> findNewProducts() throws SQLException;

	/**
	 * -查询所有商品分类
	 *
	 * @return
	 * @throws SQLException
	 */
	List<Category> findAllCategorys() throws SQLException;

	/**
	 * -查询单个商品分类
	 *
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	Category findCategoryByCid(String cid) throws SQLException;

	/**
	 * -根据分类ID查询分类下商品数量
	 *
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	int getCount(String cid) throws SQLException;

	/**
	 * -查询某分类下的商品的分页信息
	 *
	 * @param cid
	 * @param start
	 * @param count
	 * @return
	 * @throws SQLException
	 */
	List<Product> findProductListByPage(String cid, int start, int count) throws SQLException;

	/**
	 * -根据PID查找商品信息
	 *
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	Product findProductByID(String pid) throws SQLException;
}
