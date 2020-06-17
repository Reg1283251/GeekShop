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
////		获取request域中method属性
//		String methodName = request.getParameter("method");
//
////		这里判断method属性的值
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
//		获取商品类别id
		String cid = request.getParameter("cid");// 商品编号
		String cpage = request.getParameter("currentPage");// 当前页码

		int currentPage = 1;// 默认第一页
//		调用service层
		ProductService service = new ProductServiceImpl();
//		设置分页
		PageBean<Product> pageBean = paging(currentPage, cid, cpage, service);

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
		request.setAttribute("currentPage", cpage);

//		将请求以及参数转发至product_list.jsp中
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	private PageBean<Product> paging(int currentPage, String cid, String cpage, ProductService service) {
//		判断cpage是否为空
		if (cpage != null && Integer.parseInt(cpage) > 0) {
			currentPage = Integer.parseInt(cpage);
		}
		int currentCount = 6;// 每页6个商品

//		调用service层的方法查询
		PageBean<Product> pageBean = service.findProductByCid(cid, currentPage, currentCount);
		return pageBean;
	}

	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		接受参数
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String cpage = request.getParameter("currentPage");

		int currentPage = 1;

//		查询商品
		ProductService service = new ProductServiceImpl();
		Product productInfo = service.findProductByID(pid);
		Category category = service.findCategoryByCid(cid);// 查询商品分类名称

//		这里在请求里设置product,cid,currentPage,category的页面属性,传入request域中
		request.setAttribute("product", productInfo);
		request.setAttribute("cid", cid);
		request.setAttribute("category", category);
		request.setAttribute("currentPage", cpage);

//		System.out.println(productInfo);

		handleCookie(request, response, pid);

//		请求转发
		request.getRequestDispatcher("product_info.jsp").forward(request, response);
	}

	public void handleCookie(HttpServletRequest request, HttpServletResponse response, String pid) {
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
					LinkedList<String> pidLink = new LinkedList<>(pidList);// 转换成LinkedList更方便操作

//					如果cooki里包含当前的pid,移除pid并重新放到第一个(保证其在浏览记录最前列)
					if (pidLink.contains(pid)) {
						pidLink.remove(pid);
					}
					pidLink.addFirst(pid);// 无论是否重复,都添加到最新的位置

//					使用字符串拼接,重新将数组转化为字符串
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

//		创建cookie并放到响应中
		Cookie c = new Cookie("pids", pids);
		response.addCookie(c);
	}

	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	public void categoryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			categoryListJson = gson.toJson(categoryList);// 传入的变量不可以加双引号

//			缓存到redis中
			redis.set("categoryListJson", categoryListJson);

		} else {
			System.out.println("有Redis缓存,直接调用redis");
		}
		redis.close();

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(categoryListJson);
	}

	public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		1.获取商品参数
		String pid = request.getParameter("pid");
		String countStr = request.getParameter("count");
		int count = Integer.parseInt(countStr);

//		2.处理
//		查询商品		
		ProductServiceImpl service = new ProductServiceImpl();
		Product product = service.findProductByID(pid);

//		创建购物车,传入商品信息,订购数量以及总价
		CartItem item = new CartItem(product, count, product.getShop_price() * count);
//		将购物车项放入购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
		}
//		判断购物车里是否又重复的
		if (cart.getCartitem().containsKey(pid)) {
			// 获取到之前的商品项oldItem
			CartItem oldItem = cart.getCartitem().get(pid);
			// 把之前购物车里的商品数量加上新加入购物车个的商品数量
			item.setBuyNum(item.getBuyNum() + oldItem.getBuyNum());
			// 重新计算商品总价
			item.setSubTotal(item.getSubTotal() + oldItem.getSubTotal());
			// 再从总价上减去原先此商品的价格
			cart.setTotal(cart.getTotal() - oldItem.getSubTotal());
		}

//		将新的商品放入购物车
		cart.getCartitem().put(pid, item);
//		计算总价
		cart.setTotal(cart.getTotal() + item.getSubTotal());

//		将修改好的购物车放入session中
		session.setAttribute("cart", cart);

//		3.请求转发
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void delFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		从request和session中获取数据
		String pid = request.getParameter("pid");

//		获取购物车对象
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");

		if (cart != null) {
//			获取集合
			Map<String, CartItem> list = cart.getCartitem();
//			重新计算金额
			cart.setTotal(cart.getTotal() - list.get(pid).getSubTotal());

//			移除被删除的项目
			list.remove(pid);
		}

//		保存至session
		session.setAttribute("cart", cart);

//		设置响应页面路径
		response.sendRedirect(request.getContextPath() + "/cart.jsp");

	}
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		获取到session
		HttpSession session = request.getSession();
//		清除购物车只需删除session后重新转向cart.jsp页面即可
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	
}
