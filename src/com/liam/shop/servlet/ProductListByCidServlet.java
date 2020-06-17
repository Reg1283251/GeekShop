package com.liam.shop.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liam.shop.pojo.PageBean;
import com.liam.shop.pojo.Product;
import com.liam.shop.service.ProductService;
import com.liam.shop.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class ProductListByCidServlet
 */
public class ProductListByCidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductListByCidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		获取商品类别id
		String cid = request.getParameter("cid");//商品编号
		String cpage = request.getParameter("currentPage");//当前页码

		int currentPage = 1;//默认第一页
//		判断cpage是否为空
		if (cpage != null && Integer.parseInt(cpage) > 0) {
			currentPage = Integer.parseInt(cpage);
		}
		int currentCount = 6;//每页6个商品

//		调用service层
		ProductService service = new ProductServiceImpl();

//		调用service层的方法查询
		PageBean<Product> pageBean = service.findProductByCid(cid, currentPage, currentCount);

//		获取cookie中的pid
		Cookie[] cookies = request.getCookies();
		List<Product> historyList = new ArrayList<Product>();
		if (cookies != null) {

			for (Cookie c : cookies) {
				if ("pids".equals(c.getName())) {
					String pids = c.getValue();
					String[] pidarr = pids.split("-");
					for (String p : pidarr) {
						Product product = service.findProductByID(p);
						historyList.add(product);
					}
				}
			}

		}


//		将查询结果存放至请求当中
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		request.setAttribute("historyList", historyList);

//		将请求以及参数转发至product_list.jsp中
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
