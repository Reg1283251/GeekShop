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
	 * -��ѯ������Ʒ
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
	 * -��ѯ������Ʒ
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
	 * -��ѯ������Ʒ����
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
	 * -��ѯ��ҳ��Ϣ
	 */
	@Override
	public PageBean<Product> findProductByCid(String cid, int currentPage, int currentCount) {

//		1.ʵ����һ��PageBean������ŷ�ҳ����
		PageBean<Product> pageBean = new PageBean<Product>();

//		2.����currentPage��ǰҳ
		pageBean.setCurrentPage(currentPage);

//		3.����currentCountÿҳ��Ʒ������
		pageBean.setCurrentCount(currentCount);

//		4.��ȡ����Ŀ�����������ҳ��,��ҳ��=(����Ʒ��/ÿҳ����Ʒ��)
//		a).��ȡ����Ŀ��
		IProductDao dao = new IProductDaoImpl();
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);

//		b).������ҳ��
		pageBean.setTotalPage((int) Math.ceil(1.0 * (totalCount / currentCount)));

//		5.��ȡÿҳ����Ʒ����
//		start��ʼҳ�±�
//		���currentPage�ǵ�һҳ,��start��0,����(0~11)����ǵڶ�ҳ,��start��12��ʼ,����(12~23))
		int start = (currentPage - 1) * currentCount;
		List<Product> list = null;
		try {
			list = dao.findProductListByPage(cid, start, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		����List
		pageBean.setList(list);

//		����pageBean����
		return pageBean;
	}

	/**
	 * -����pid������Ʒ��ϸ��Ϣ
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
	 * -��ѯ����������Ϣ
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
