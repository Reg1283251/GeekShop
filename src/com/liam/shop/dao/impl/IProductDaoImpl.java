package com.liam.shop.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.liam.shop.dao.IProductDao;
import com.liam.shop.pojo.Category;
import com.liam.shop.pojo.Product;
import com.liam.utils.DataSourceUtils;

public class IProductDaoImpl implements IProductDao {

	@Override
	public List<Product> findHotProducts() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = ? limit ?;";
		Object[] params = {1,9};
		
		List<Product> result =  runner.query(sql,new BeanListHandler<>(Product.class),params); 
		
		return result;
	}

	@Override
	public List<Product> findNewProducts() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate DESC limit ?;";
		Object[] param = {9};
		
		List<Product> result =  runner.query(sql,new BeanListHandler<>(Product.class),param); 
		
		return result;
	}

	@Override
	public List<Category> findAllCategorys() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		List<Category> result = runner.query(sql, new BeanListHandler<>(Category.class));
		return result;
	}
	
	@Override
	public Category findCategoryByCid(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select cname from category where cid=?";
		return runner.query(sql, new BeanHandler<>(Category.class),cid);
	}

	@Override
	public int getCount(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid = ?;";
//		Object[] param = {cid};
		
		Long row = (Long)runner.query(sql, new ScalarHandler(),cid);
		return row.intValue();
	}

	/**
	 * @param cid   商品类别
	 * @param start limit的开始下标
	 * @param count limit的结束下标
	 */
	@Override
	public List<Product> findProductListByPage(String cid, int start, int count) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? limit ?,?;";
		Object[] params = {cid,start,count};
		List<Product> result = runner.query(sql, new BeanListHandler<>(Product.class),params);
		return result;
	}

	@Override
	public Product findProductByID(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ?;";
		return runner.query(sql, new BeanHandler<>(Product.class),pid);
	}


}
