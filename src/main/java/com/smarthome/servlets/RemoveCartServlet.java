package com.smarthome.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smarthome.models.Product;
import com.smarthome.service.Cart;
import com.smarthome.service.ProductService;

public class RemoveCartServlet extends HttpServlet {

	ProductService productService;

	public void init() {
		productService = new ProductService();
		try {
			productService.loadProducts();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		Cart cart = (Cart) session.getAttribute("cart");
		
		String productIdStr = request.getParameter("productId");
		
		try {
			int productId = Integer.parseInt(productIdStr);
			Product product = productService.getProductbyId(productId);
		
			if (product != null) {
                cart.removeProduct(product);
                session.setAttribute("cart", cart);
                response.sendRedirect("viewcart.jsp"); 
            } else {
                response.sendRedirect("error.jsp");
            }
		} catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
		
	}

}
