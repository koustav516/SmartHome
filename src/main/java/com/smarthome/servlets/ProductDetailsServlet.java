package com.smarthome.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smarthome.models.Product;
import com.smarthome.service.ProductService;

public class ProductDetailsServlet extends HttpServlet {

    private ProductService productService;

    public void init() {
        productService = new ProductService();
        try {
            productService.loadProducts();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productIdStr = request.getParameter("productID");
        int productId = Integer.parseInt(productIdStr);

        Product product = null;
        try {
            product = productService.getProductDetailsbyID(productId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        request.setAttribute("product", product);

        RequestDispatcher dispatcher = request.getRequestDispatcher("productDetails.jsp");
        dispatcher.forward(request, response);
    }

}
