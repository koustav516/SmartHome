package com.smarthome.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smarthome.models.Product;
import com.smarthome.service.ProductService;

public class AllProductsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();

        List<Product> productList = productService.getAllProducts();
        request.setAttribute("productList", productList);

        request.getRequestDispatcher("/allProducts.jsp").forward(request, response);
    }

}
