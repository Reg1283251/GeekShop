package com.liam.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.liam.shop.pojo.Category;
import com.liam.shop.service.ProductService;
import com.liam.shop.service.impl.ProductServiceImpl;
import com.liam.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CategoryListServlet
 */
public class CategoryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		获取参数
//		调用service层查询数据
//		判断redis中有无分类的缓存信息,有则直接调用,没有则查询数据库
        Jedis redis = JedisPoolUtils.getJedis();
        String categoryListJson = redis.get("categoryListJson");

        if (categoryListJson == null) {
            System.out.println("无缓存,需要查询数据库");

//			查询数据库
            ProductService service = new ProductServiceImpl();
            List<Category> categoryList = service.findAllCategorys();
//			转化为json数据格式
            Gson gson = new Gson();
            categoryListJson = gson.toJson(categoryList);//传入的变量不可以加双引号

//			缓存到redis中
            redis.set("categoryListJson", categoryListJson);

        } else {
            System.out.println("有Redis缓存,直接调用redis");
        }
        redis.close();

        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.write(categoryListJson);


//		使用Ajax获取分类列表
////		获取参数
////		调用service层查询
//		ProductService service = new ProductServiceImpl();
//		List<Category> categoryList = service.findAllCategorys();
////		利用Gson工具将categoryList转换为Json数据格式
//		Gson gson = new Gson();
//		String categoryListJson = gson.toJson(categoryList);
//
////		显示
//		response.setContentType("text/html;charset=utf-8");
//		PrintWriter pw = response.getWriter();
//		pw.write(categoryListJson);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
