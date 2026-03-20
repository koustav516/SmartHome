package com.smarthome.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smarthome.models.User;
import com.smarthome.service.UserService;

public class LoginServlet extends HttpServlet {

	private UserService userService;

	public void init() {
		userService = new UserService();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = userService.authenticateUser(email, password);

		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			session.setAttribute("user_id", user.getId());
			session.setAttribute("userName", user.getName());

			response.sendRedirect("index.jsp");

		} else {
			response.sendRedirect("login.jsp?message=Invalid username or password");
		}
	}

}
