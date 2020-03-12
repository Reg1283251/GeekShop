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
//		���ܲ���
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");

//		��ѯ��Ʒ
		ProductService service = new ProductServiceImpl();
		Product productInfo = service.findProductByID(pid);
		Category category = service.findCategoryByCid(cid);// ��ѯ��Ʒ��������

//		����������������product,cid,currentPage,category��ҳ������,����request����
		request.setAttribute("product", productInfo);
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("category", category);
//		System.out.println(productInfo);

		handleCookie(request, response, pid);

//		����ת��
		request.getRequestDispatcher("product_info.jsp").forward(request, response);

	}

	/**
	 * -ʹ��cookie�������������¼
	 * 
	 * @param request
	 * @param response
	 */
	private void handleCookie(HttpServletRequest request, HttpServletResponse response, String pid) {

		String pids = null;
//		��cookie�ж�ȡ�Ƿ��ɽ�"pids"��ֵ
		Cookie[] cookies = request.getCookies();
//		�ж�cookie�Ƿ�Ϊ��
		if (cookies != null) {
//			����cookie,Ѱ��pid
			for (Cookie c : cookies) {
//				�ж��Ƿ���pid
				if ("pids".equals(c.getName())) {
//					��ȡcookie��ֵ
					pids = c.getValue();
					
//					����ȡ��pidת��Ϊ����,�������pid��˳��
//					��","���ֽ��,�ָ�pids������
					String[] pidStr = pids.split("-");
					List<String> pidList = Arrays.asList(pidStr);
					LinkedList<String> pidLink = new LinkedList<>(pidList);//ת����LinkedList���������
					
//					���cooki�������ǰ��pid,�Ƴ�pid�����·ŵ���һ��(��֤���������¼��ǰ��)
					if (pidLink.contains(pid)) {
						pidLink.remove(pid);
					}
					pidLink.addFirst(pid);//�����Ƿ��ظ�,����ӵ����µ�λ��
					
//					ʹ���ַ���ƴ��,���½�����ת��Ϊ�ַ���
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
		
//		����cookie���ŵ���Ӧ��
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
