package com.smarthome.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smarthome.models.Product;
import com.smarthome.service.ProductRecommendationService;
import com.smarthome.utils.Category;
import com.smarthome.utils.DbConnection;

public class RecommendProductServlet extends HttpServlet {

    ProductRecommendationService productRecommendationService;

    public void init() {
        productRecommendationService = new ProductRecommendationService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productQuery = request.getParameter("productQuery");
        try (Connection conn = DbConnection.initializeDb()) {
            String queryEmbedding = productRecommendationService.generateEmbedding(productQuery);
            Map<Integer, List<Double>> productEmbeddings = productRecommendationService.fetchProductEmbeddings(conn);

            List<Integer> recommendedProductIds = productRecommendationService.recommendProducts(queryEmbedding,
                    productEmbeddings);

            List<Product> recommendedProducts = fetchProductDetails(recommendedProductIds, conn);
            request.setAttribute("recommendedProducts", recommendedProducts);

            request.getRequestDispatcher("recommendedProducts.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error processing recommendation", e);
        }
    }

    private List<Product> fetchProductDetails(List<Integer> productIds, Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE id IN (" +
                productIds.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDesc(rs.getString("desc"));
                String categoryStr = rs.getString("category").toUpperCase().replace(" ", "_");
                Category category = Category.valueOf(categoryStr);
                product.setCategory(category);
                product.setPrice(rs.getDouble("price"));
                products.add(product);
            }
        }
        return products;
    }

}
