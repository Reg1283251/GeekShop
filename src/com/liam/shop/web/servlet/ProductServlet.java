package com.liam.shop.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.liam.shop.pojo.Cart;
import com.liam.shop.pojo.CartItem;
import com.liam.shop.pojo.Category;
import com.liam.shop.pojo.PageBean;
import com.liam.shop.pojo.Product;
import com.liam.shop.service.ProductService;
import com.liam.shop.service.impl.ProductServiceImpl;
import com.liam.utils.JedisPoolUtils;
import com.mysql.cj.Session;

import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/product")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
////		��ȡrequest����method����
//		String methodName = request.getParameter("method");
//
////		�����ж�method���Ե�ֵ
//		if ("categoryList".equals(methodName)) {
//			categoryList(request, response);
//		} else if ("index".equals(methodName)) {
//			index(request, response);
//		} else if ("productInfo".equals(methodName)) {
//			productInfo(request, response);
//		} else if ("productListByCid".equals(methodName)) {
//			productListByCid(request, response);
//		}
//	}

	public void productListByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		��ȡ��Ʒ���id
		String cid = request.getParameter("cid");// ��Ʒ���
		String cpage = request.getParameter("currentPage");// ��ǰҳ��

		int currentPage = 1;// Ĭ�ϵ�һҳ
//		����service��
		ProductService service = new ProductServiceImpl();
//		���÷�ҳ
		PageBean<Product> pageBean = paging(currentPage, cid, cpage, service);

//		��ȡcookie�е�pid
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

//		����ѯ��������������
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		request.setAttribute("historyList", historyList);
		request.setAttribute("currentPage", cpage);

//		�������Լ�����ת����product_list.jsp��
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	private PageBean<Product> paging(int currentPage, String cid, String cpage, ProductService service) {
//		�ж�cpage�Ƿ�Ϊ��
		if (cpage != null && Integer.parseInt(cpage) > 0) {
			currentPage = Integer.parseInt(cpage);
		}
		int currentCount = 6;// ÿҳ6����Ʒ

//		����service��ķ�����ѯ
		PageBean<Product> pageBean = service.findProductByCid(cid, currentPage, currentCount);
		return pageBean;
	}

	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		���ܲ���
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String cpage = request.getParameter("currentPage");

		int currentPage = 1;

//		��ѯ��Ʒ
		ProductService service = new ProductServiceImpl();
		Product productInfo = service.findProductByID(pid);
		Category category = service.findCategoryByCid(cid);// ��ѯ��Ʒ��������

//		����������������product,cid,currentPage,category��ҳ������,����request����
		request.setAttribute("product", productInfo);
		request.setAttribute("cid", cid);
		request.setAttribute("category", category);
		request.setAttribute("currentPage", cpage);

//		System.out.println(productInfo);

		handleCookie(request, response, pid);

//		����ת��
		request.getRequestDispatcher("product_info.jsp").forward(request, response);
	}

	public void handleCookie(HttpServletRequest request, HttpServletResponse response, String pid) {
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
					LinkedList<String> pidLink = new LinkedList<>(pidList);// ת����LinkedList���������

//					���cooki�������ǰ��pid,�Ƴ�pid�����·ŵ���һ��(��֤���������¼��ǰ��)
					if (pidLink.contains(pid)) {
						pidLink.remove(pid);
					}
					pidLink.addFirst(pid);// �����Ƿ��ظ�,����ӵ����µ�λ��

//					ʹ���ַ���ƴ��,���½�����ת��Ϊ�ַ���
					StringBuffer sBuffer = new StringBuffer();

					for (int i = 0; i < pidLink.size() && i < 7; i++) {
						sBuffer.append(pidLink.get(i));
						sBuffer.append("-");
					}

					sBuffer.substring(0, sBuffer.length() - 1);

					pids = sBuffer.toString();
				}
			}
			System.out.println("debug:" + pids);
		}

//		����cookie���ŵ���Ӧ��
		Cookie c = new Cookie("pids", pids);
		response.addCookie(c);
	}

	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		����service��
		ProductServiceImpl service = new ProductServiceImpl();
		List<Product> hotProducts = service.findHotProducts();
		List<Product> newProducts = service.findNewProducts();
//		List<Category> categoryList = service.findAllCategorys();

//		����ҳ������		
		request.setAttribute("hotProducts", hotProducts);
		request.setAttribute("newProducts", newProducts);
//		request.setAttribute("categoryList", categoryList);

//		����ת����ҳ
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	public void categoryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			categoryListJson = gson.toJson(categoryList);// ����ı��������Լ�˫����

//			���浽redis��
			redis.set("categoryListJson", categoryListJson);

		} else {
			System.out.println("��Redis����,ֱ�ӵ���redis");
		}
		redis.close();

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(categoryListJson);
	}

	public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		1.��ȡ��Ʒ����
		String pid = request.getParameter("pid");
		String countStr = request.getParameter("count");
		int count = Integer.parseInt(countStr);

//		2.����
//		��ѯ��Ʒ		
		ProductServiceImpl service = new ProductServiceImpl();
		Product product = service.findProductByID(pid);

//		�������ﳵ,������Ʒ��Ϣ,���������Լ��ܼ�
		CartItem item = new CartItem(product, count, product.getShop_price() * count);
//		�����ﳵ����빺�ﳵ
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
		}
//		�жϹ��ﳵ���Ƿ����ظ���
		if (cart.getCartitem().containsKey(pid)) {
			// ��ȡ��֮ǰ����Ʒ��oldItem
			CartItem oldItem = cart.getCartitem().get(pid);
			// ��֮ǰ���ﳵ�����Ʒ���������¼��빺�ﳵ������Ʒ����
			item.setBuyNum(item.getBuyNum() + oldItem.getBuyNum());
			// ���¼�����Ʒ�ܼ�
			item.setSubTotal(item.getSubTotal() + oldItem.getSubTotal());
			// �ٴ��ܼ��ϼ�ȥԭ�ȴ���Ʒ�ļ۸�
			cart.setTotal(cart.getTotal() - oldItem.getSubTotal());
		}

//		���µ���Ʒ���빺�ﳵ
		cart.getCartitem().put(pid, item);
//		�����ܼ�
		cart.setTotal(cart.getTotal() + item.getSubTotal());

//		���޸ĺõĹ��ﳵ����session��
		session.setAttribute("cart", cart);

//		3.����ת��
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void delFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		��request��session�л�ȡ����
		String pid = request.getParameter("pid");

//		��ȡ���ﳵ����
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		
		if (cart != null) {
//			��ȡ����
			Map<String, CartItem> list = cart.getCartitem();
//			���¼�����
			cart.setTotal(cart.getTotal()-list.get(pid).getSubTotal());
			
//			�Ƴ���ɾ������Ŀ
			list.remove(pid);
		}
		
//		������session
		session.setAttribute("cart", cart);
		
//		������Ӧҳ��·��
		response.sendRedirect(request.getContextPath()+"/cart.jsp");

	}
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		��ȡ��session
		HttpSession session = request.getSession();
//		������ﳵֻ��ɾ��session������ת��cart.jspҳ�漴��
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	
}
