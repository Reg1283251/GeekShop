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
	 * -利用反射,替代ProductServlet和UserSerlvet的额外开销,降低代码冗余
	 *
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
//		获取请求中的方法
			String methodName = request.getParameter("method");

//		使用反射实现通过方法名字得到方法对象,使用方法对象执行方法
//			得到请求的字节码
			Class<? extends BaseServlet> clazz = this.getClass();
			
//			根据字节码和方法名获取方法对象
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//			执行方法
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
