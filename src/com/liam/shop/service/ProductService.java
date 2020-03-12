package com.liam.shop.service;

import java.util.List;

import com.liam.shop.pojo.Category;
import com.liam.shop.pojo.PageBean;
import com.liam.shop.pojo.Product;

public interface ProductService {
	
	public List<Product> findHotProducts();
	public List<Product> findNewProducts();
	public List<Category> findAllCategorys();
	public PageBean<Product> findProductByCid(String cid,int currentPage,int currentCount);
	public Product findProductByID(String pid);
	public Category findCategoryByCid(String cid);

}
