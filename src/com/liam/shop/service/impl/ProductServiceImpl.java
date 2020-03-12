package com.liam.shop.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.liam.shop.dao.IProductDao;
import com.liam.shop.dao.impl.IProductDaoImpl;
import com.liam.shop.pojo.Category;
import com.liam.shop.pojo.PageBean;
import com.liam.shop.pojo.Product;
import com.liam.shop.service.ProductService;

public class ProductServiceImpl implements ProductService {

	/**
	 * -查询热门商品
	 */
	@Override
	public List<Product> findHotProducts() {
		IProductDao dao = new IProductDaoImpl();
		List<Product> products = null;
		try {
			products = dao.findHotProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	/**
	 * -查询最新商品
	 */
	@Override
	public List<Product> findNewProducts() {
		IProductDao dao = new IProductDaoImpl();
		List<Product> products = null;
		try {
			products = dao.findNewProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	/**
	 * -查询所有商品分类
	 */
	@Override
	public List<Category> findAllCategorys() {
		IProductDao dao = new IProductDaoImpl();
		List<Category> categorys = null;
		try {
			categorys = dao.findAllCategorys();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categorys;
	}

	/**
	 * -查询分页信息
	 */
	@Override
	public PageBean<Product> findProductByCid(String cid, int currentPage, int currentCount) {

//		1.实例化一个PageBean用来存放分页数据
		PageBean<Product> pageBean = new PageBean<Product>();

//		2.设置currentPage当前页
		pageBean.setCurrentPage(currentPage);

//		3.设置currentCount每页商品的数量
		pageBean.setCurrentCount(currentCount);

//		4.获取总条目数并计算出总页数,总页数=(总商品数/每页的商品数)
//		a).获取总条目数
		IProductDao dao = new IProductDaoImpl();
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);

//		b).计算总页数
		pageBean.setTotalPage((int) Math.ceil(1.0 * (totalCount / currentCount)));

//		5.获取每页的商品详情
//		start起始页下标
//		如果currentPage是第一页,则start是0,代表(0~11)如果是第二页,则start从12开始,代表(12~23))
		int start = (currentPage - 1) * currentCount;
		List<Product> list = null;
		try {
			list = dao.findProductListByPage(cid, start, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		设置List
		pageBean.setList(list);

//		返回pageBean对象
		return pageBean;
	}

	/**
	 * -根据pid返回商品详细信息
	 */
	@Override
	public Product findProductByID(String pid) {
		IProductDao dao = new IProductDaoImpl();
		Product p = null;
		try {
			p = dao.findProductByID(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * -查询单个分类信息
	 */
	@Override
	public Category findCategoryByCid(String cid) {
		IProductDao dao = new IProductDaoImpl();
		Category category = null;
		try {
			category = dao.findCategoryByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
		
	}

}
