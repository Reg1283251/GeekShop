package com.liam.shop.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liam.shop.pojo.Category;
import com.liam.shop.pojo.Product;
import com.liam.shop.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		调用service层
		ProductServiceImpl service = new ProductServiceImpl();
		List<Product> hotProducts = service.findHotProducts();
		List<Product> newProducts = service.findNewProducts();
//		List<Category> categoryList = service.findAllCategorys();

//		设置页面属性		
		request.setAttribute("hotProducts", hotProducts);
		request.setAttribute("newProducts", newProducts);
//		request.setAttribute("categoryList", categoryList);

//		请求转发首页
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
