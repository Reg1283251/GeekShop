package com.liam.shop.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
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
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
					//String subject, String email, String emailMsg
					MailUtils.sendMail("�����̳ǻ�Ա����", user.getEmail(), "ף��" + user.getUsername()
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
		}
		else if ("active".equals(op)) {
//			�������
			System.out.println("active");
		}
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
