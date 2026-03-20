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
import com.smarthome.service.OrderService;

public class CancelOrderServlet extends HttpServlet{
	
	private OrderService orderService = new OrderService();
	

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderNumber = request.getParameter("orderNumber");
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");
        try {
            boolean isCanceled = orderService.cancelOrder(orderNumber);
            if (isCanceled) {
            	session.setAttribute("cancelMessage", "Order: " + orderNumber + " has been canceled successfully.");
            	List<Order> updatedOrders = orderService.getOrdersById(userId);
            	session.setAttribute("orders", updatedOrders);
            } else {
            	session.setAttribute("cancelMessage", "Failed to cancel Order: " + orderNumber);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing the request.");
        }

        request.getRequestDispatcher("/orderhistory.jsp").forward(request, response);
    }
	
}
