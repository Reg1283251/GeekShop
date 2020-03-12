package com.liam.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaseServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * -���÷���,���ProductServlet��UserSerlvet�Ķ��⿪��,���ʹ�������
	 *
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
//		��ȡ�����еķ���
			String methodName = request.getParameter("method");

//		ʹ�÷���ʵ��ͨ���������ֵõ���������,ʹ�÷�������ִ�з���
//			�õ�������ֽ���
			Class<? extends BaseServlet> clazz = this.getClass();
			
//			�����ֽ���ͷ�������ȡ��������
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//			ִ�з���
			method.invoke(this, request, response);

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

}
