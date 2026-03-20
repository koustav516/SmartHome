package com.smarthome.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smarthome.models.User;
import com.smarthome.service.UserService;

public class RegisterServlet extends HttpServlet{
	
	private UserService userService;
	private User user;
	
	public void init() {
		userService = new UserService();
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        user = new User(name, email, password, role);
        
        boolean registrationSuccessful = userService.userRegistration(user);
        
        if(registrationSuccessful) {
        	response.sendRedirect("login.jsp");
        } else {
        	response.getWriter().println("Registration Unsuccessful");
        }
        
	}
}
