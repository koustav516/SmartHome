package com.smarthome.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smarthome.models.Product;
import com.smarthome.models.User;
import com.smarthome.service.Cart;
import com.smarthome.service.ProductService;

public class AddToCartServlet extends HttpServlet {

	ProductService productService;

	public void init() {
		productService = new ProductService();
		try {
			productService.loadProducts();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			res.sendRedirect("login.jsp");
			return;
		}

		int productId = Integer.parseInt(req.getParameter("productID"));

		try {
			Product product = productService.getProductbyId(productId);
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart == null) {
				cart = new Cart();
				session.setAttribute("cart", cart);
			}
			cart.addProduct(product);
			res.sendRedirect("viewcart.jsp");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
