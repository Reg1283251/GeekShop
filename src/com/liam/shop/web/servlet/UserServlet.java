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
////		��ȡrequest����method����
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
//		��ȡop����
		String op = request.getParameter("op");
		System.out.println(op);
//		�ж�op�����о���Ĳ���
		if ("register".equals(op)) {
			System.out.println("register");
//			ע�����
//		1.���ղ���
			User user = new User();

//		ʹ��beanUtils
//		ת�����ڸ�ʽ
			DateConverter dateconvert = new DateConverter();
			dateconvert.setPatterns(new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
			ConvertUtils.register(dateconvert, Date.class);

			try {
//			����������Ĳ���
				BeanUtils.copyProperties(user, request.getParameterMap());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

//		����һ��UID
			user.setUid(CommonUtils.getUUID());
//		����һ��������
			user.setCode(CommonUtils.getUUID());
//		���ϣ������������

//		2.����service�㴦��
			UserService service = new IUserServiceImpl();
			int result = service.register(user);

//		3.�ض����������ת�� ������������jspҳ��
			if (result > 0) {
				try {
					// String subject, String email, String emailMsg
					MailUtils.sendMail("�����̳ǻ�Ա����", user.getEmail(),
							"ף��" + user.getUsername()
									+ "��ע��ɹ�,������Ӽ���<a href=\"http://localhost:6080/GeekShop/active?op=active&code="
									+ user.getCode() + "\">�����Ա</a>");
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
//			�������
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
			msg = "{\"error\":\"û�д����û���\"}";
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
			response.getWriter().println("����ʧ��");
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
