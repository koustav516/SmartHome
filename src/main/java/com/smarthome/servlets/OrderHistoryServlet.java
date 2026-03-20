package com.smarthome.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smarthome.models.Order;
import com.smarthome.models.User;
import com.smarthome.service.OrderService;

public class OrderHistoryServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (user != null) {
            OrderService orderService = new OrderService();
            try {
                List<Order> orders = orderService.getOrdersById(user.getId());
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("/orderhistory.jsp").forward(request, response);
            } catch (SQLException | ClassNotFoundException | ServletException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("login.jsp");
        }
	}
	
}
