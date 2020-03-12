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
//		��ȡ��Ʒ���id
		String cid = request.getParameter("cid");//��Ʒ���
		String cpage = request.getParameter("currentPage");//��ǰҳ��
		
		int currentPage = 1;//Ĭ�ϵ�һҳ
//		�ж�cpage�Ƿ�Ϊ��
		if (cpage != null && Integer.parseInt(cpage)>0) {
			currentPage = Integer.parseInt(cpage);
		}
		int currentCount = 6;//ÿҳ6����Ʒ
		
//		����service��
		ProductService service = new ProductServiceImpl();
		
//		����service��ķ�����ѯ
		PageBean<Product> pageBean = service.findProductByCid(cid, currentPage, currentCount);
		
//		��ȡcookie�е�pid
		Cookie[] cookies = request.getCookies();
		List<Product> historyList = new ArrayList<Product>();
		if (cookies != null) {
			
			for (Cookie c: cookies) {
				if ("pids".equals(c.getName())) {
					String pids = c.getValue();
					String[] pidarr = pids.split("-");
					for (String p : pidarr) {
						Product product =service.findProductByID(p);
						historyList.add(product);
					}
				}
			}
			
		}
		
		
//		����ѯ��������������
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		request.setAttribute("historyList", historyList);
		
//		�������Լ�����ת����product_list.jsp��
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
