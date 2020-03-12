package com.liam.shop.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liam.shop.pojo.Category;
import com.liam.shop.pojo.Product;
import com.liam.shop.service.ProductService;
import com.liam.shop.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class ProductInfoServlet
 */
public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductInfoServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		接受参数
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");

//		查询商品
		ProductService service = new ProductServiceImpl();
		Product productInfo = service.findProductByID(pid);
		Category category = service.findCategoryByCid(cid);// 查询商品分类名称

//		这里在请求里设置product,cid,currentPage,category的页面属性,传入request域中
		request.setAttribute("product", productInfo);
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("category", category);
//		System.out.println(productInfo);

		handleCookie(request, response, pid);

//		请求转发
		request.getRequestDispatcher("product_info.jsp").forward(request, response);

	}

	/**
	 * -使用cookie技术保存浏览记录
	 * 
	 * @param request
	 * @param response
	 */
	private void handleCookie(HttpServletRequest request, HttpServletResponse response, String pid) {

		String pids = null;
//		从cookie中读取是否由叫"pids"的值
		Cookie[] cookies = request.getCookies();
//		判断cookie是否为空
		if (cookies != null) {
//			遍历cookie,寻找pid
			for (Cookie c : cookies) {
//				判断是否是pid
				if ("pids".equals(c.getName())) {
//					获取cookie的值
					pids = c.getValue();
					
//					将获取的pid转化为数组,方便操作pid的顺序
//					将","作分界符,分割pids成数组
					String[] pidStr = pids.split("-");
					List<String> pidList = Arrays.asList(pidStr);
					LinkedList<String> pidLink = new LinkedList<>(pidList);//转换成LinkedList更方便操作
					
//					如果cooki里包含当前的pid,移除pid并重新放到第一个(保证其在浏览记录最前列)
					if (pidLink.contains(pid)) {
						pidLink.remove(pid);
					}
					pidLink.addFirst(pid);//无论是否重复,都添加到最新的位置
					
//					使用字符串拼接,重新将数组转化为字符串
					StringBuffer sBuffer = new StringBuffer();
					
					for (int i = 0; i < pidLink.size() && i<7; i++) {
						sBuffer.append(pidLink.get(i));
						sBuffer.append("-");
					}
					
					sBuffer.substring(0, sBuffer.length()-1);
					
					pids = sBuffer.toString();
				}
			}
			System.out.println("debug:"+pids);
		}
		
//		创建cookie并放到响应中
		Cookie c = new Cookie("pids",pids);
		response.addCookie(c);
		

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
