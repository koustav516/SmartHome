package com.smarthome.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.smarthome.models.Product;
import com.smarthome.service.ProductService;

public class AjaxServlet extends HttpServlet {

    ProductService productService;

    public void init() {
        productService = new ProductService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String term = request.getParameter("term");
        List<Product> products = new ArrayList<>();
        try {
            products = productService.getMatchingProducts(term);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String json = gson.toJson(products);
        out.print(json);
        out.flush();
    }

}
