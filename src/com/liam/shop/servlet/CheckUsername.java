package com.liam.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liam.shop.service.UserService;
import com.liam.shop.service.impl.IUserServiceImpl;

/**
 * Servlet implementation class CheckUsername
 */
public class CheckUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
//		1
		String username = request.getParameter("username");
		String msg = "";
		if (username == null) {
			msg = "{\"error\":\"没有传入用户名\"}";
		}else {
//			2
			UserService is = new IUserServiceImpl();
			Boolean result = is.findByUsername(username);
			msg = "{\"canUse\":"+result+"}";
		}
//		3
		printWriter.println(msg);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
