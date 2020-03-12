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
		
//		��ȡ����
//		����service���ѯ����
//		�ж�redis�����޷���Ļ�����Ϣ,����ֱ�ӵ���,û�����ѯ���ݿ�
		Jedis redis = JedisPoolUtils.getJedis();
		String categoryListJson = redis.get("categoryListJson");
		
		if (categoryListJson == null) {
			System.out.println("�޻���,��Ҫ��ѯ���ݿ�");
			
//			��ѯ���ݿ�
			ProductService service = new ProductServiceImpl();
			List<Category> categoryList = service.findAllCategorys();
//			ת��Ϊjson���ݸ�ʽ
			Gson gson = new Gson();
			categoryListJson = gson.toJson(categoryList);//����ı��������Լ�˫����
			
//			���浽redis��
			redis.set("categoryListJson", categoryListJson);
			
		}else {
			System.out.println("��Redis����,ֱ�ӵ���redis");
		}
		redis.close();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(categoryListJson);
		
		
		
		
		
//		ʹ��Ajax��ȡ�����б�
////		��ȡ����
////		����service���ѯ
//		ProductService service = new ProductServiceImpl();
//		List<Category> categoryList = service.findAllCategorys();
////		����Gson���߽�categoryListת��ΪJson���ݸ�ʽ
//		Gson gson = new Gson();
//		String categoryListJson = gson.toJson(categoryList);
//
////		��ʾ
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
