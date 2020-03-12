package com.liam.shop.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.liam.shop.pojo.User;
import com.liam.shop.service.UserService;
import com.liam.shop.service.impl.IUserServiceImpl;
import com.liam.utils.CommonUtils;
import com.liam.utils.MailUtils;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
////		获取request域中method属性
//		String methodName = request.getParameter("method");
//
//		if ("active".equals(methodName)) {
//			active(request, response);
//		} else if ("check".equals(methodName)) {
//			check(request, response);
//		} else if ("userregister".equals(methodName)) {
//			userregister(request, response);
//		}
//	}

	public void userregister(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		获取op参数
		String op = request.getParameter("op");
		System.out.println(op);
//		判断op来进行具体的操作
		if ("register".equals(op)) {
			System.out.println("register");
//			注册操作
//		1.接收参数
			User user = new User();

//		使用beanUtils
//		转换日期格式
			DateConverter dateconvert = new DateConverter();
			dateconvert.setPatterns(new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
			ConvertUtils.register(dateconvert, Date.class);

			try {
//			拷贝请求里的参数
				BeanUtils.copyProperties(user, request.getParameterMap());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

//		生成一个UID
			user.setUid(CommonUtils.getUUID());
//		生成一个激活码
			user.setCode(CommonUtils.getUUID());
//		以上，参数设置完毕

//		2.调用service层处理
			UserService service = new IUserServiceImpl();
			int result = service.register(user);

//		3.重定向或者请求转发 将处理结果交给jsp页面
			if (result > 0) {
				try {
					// String subject, String email, String emailMsg
					MailUtils.sendMail("极客商城会员激活", user.getEmail(),
							"祝贺" + user.getUsername()
									+ "，注册成功,点击链接激活<a href=\"http://localhost:6080/GeekShop/active?op=active&code="
									+ user.getCode() + "\">激活会员</a>");
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				response.sendRedirect("registerSuccess.jsp");
			} else {
				response.sendRedirect("registerFail.jsp");
			}
		} else if ("active".equals(op)) {
//			激活操作
			System.out.println("active");
		}
	}

	public void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
//		1
		String username = request.getParameter("username");
		String msg = "";
		if (username == null) {
			msg = "{\"error\":\"没有传入用户名\"}";
		} else {
//			2
			UserService is = new IUserServiceImpl();
			Boolean result = is.findByUsername(username);
			msg = "{\"canUse\":" + result + "}";
		}
//		3
		printWriter.println(msg);

	}

	public void active(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String activeCode = request.getParameter("code");
		UserService service = new IUserServiceImpl();
		int result = service.active(activeCode);

		if (result > 0) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} else {
			response.getWriter().println("激活失败");
		}
	}

//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		doGet(request, response);
//	}

}
