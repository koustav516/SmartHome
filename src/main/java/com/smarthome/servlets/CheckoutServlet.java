package com.smarthome.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smarthome.models.Store;
import com.smarthome.models.User;
import com.smarthome.service.Cart;
import com.smarthome.service.OrderService;
import com.smarthome.service.StoreService;

public class CheckoutServlet extends HttpServlet {

	Date deliveryDate;
	HttpSession session;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		List<Store> storeList = new ArrayList<>();

		try {
			StoreService storeService = new StoreService();
			storeList = storeService.getAllStores();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("storeList", storeList);
		request.getRequestDispatcher("/checkout.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");

		if (cart == null || cart.getProducts().isEmpty()) {
			response.sendRedirect("viewcart.jsp");
			return;
		}
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipCode = request.getParameter("zipCode");
		String deliveryOption = request.getParameter("deliveryOption");
		String storeLocation = request.getParameter("storeLocation");

		User loggedInUser = (User) session.getAttribute("user");
		int userId = loggedInUser.getId();
		String name = loggedInUser.getName();
		String email = loggedInUser.getEmail();

		OrderService orderService = new OrderService();
		String orderNumber = null;
		Timestamp orderDate = null;
		List<Store> storeList = new ArrayList<>();
		try {
			orderNumber = orderService.processOrder(cart, name, email, address, city, state, zipCode, deliveryOption,
					storeLocation, userId);
			orderDate = orderService.getOrderDate(orderNumber);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(orderDate);
			calendar.add(Calendar.DAY_OF_MONTH, 14);
			deliveryDate = new Date(calendar.getTimeInMillis());
			StoreService storeService = new StoreService();
			storeList = storeService.getAllStores();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		session.setAttribute("address", address);
		session.setAttribute("deliveryOption", deliveryOption);
		session.setAttribute("storeLocation", storeLocation);
		session.setAttribute("orderDate", orderDate);
		session.setAttribute("deliveryDate", deliveryDate);
		session.setAttribute("storeList", storeList);
		session.setAttribute("orderNumber", orderNumber);
		response.sendRedirect("orderconfirmation.jsp");
	}

}
