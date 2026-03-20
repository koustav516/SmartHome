package com.smarthome.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smarthome.models.Product;
import com.smarthome.service.ProductService;
import com.smarthome.utils.Category;

public class ProductServlet extends HttpServlet {

	private ProductService productService;

	public void init() {
		productService = new ProductService();
		try {
			productService.loadProducts();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String paramCategory = request.getParameter("category");

		if (paramCategory != null) {
			session.setAttribute("lastViewedCategory", paramCategory);
			Category category = Category.valueOf(paramCategory.toUpperCase().replace(" ", "_"));

			List<Product> products = productService.getProductsbyCategory(category);
			request.setAttribute("categoryName", category.name());
			request.setAttribute("products", products);

		}

		request.getRequestDispatcher("/products.jsp").forward(request, response);
	}

}
