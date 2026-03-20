package com.smarthome.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smarthome.models.Product;
import com.smarthome.models.Store;
import com.smarthome.models.User;
import com.smarthome.service.ProductService;
import com.smarthome.service.StoreService;
import com.smarthome.service.ReviewService;
import com.smarthome.service.UserService;

public class SubmitReviewServlet extends HttpServlet {
    ProductService productService;
    UserService userService;
    StoreService storeService;

    public void init() {
        productService = new ProductService();
        storeService = new StoreService();
        try {
            productService.loadProducts();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Product> productList = productService.getAllProducts();
        int productID = Integer.parseInt(request.getParameter("productID"));

        Product product = null;
        List<Store> storeList = new ArrayList<>();
        try {
            product = productService.getProductbyId(productID);
            storeList = storeService.getAllStores();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String productName = product.getName();
        String productCategory = product.getCategory().toString();
        double productPrice = product.getPrice();

        session.setAttribute("storeList", storeList);

        request.setAttribute("productID", productID);
        request.setAttribute("productName", productName);
        request.setAttribute("productCategory", productCategory);
        request.setAttribute("productPrice", productPrice);

        request.setAttribute("productList", productList);
        request.getRequestDispatcher("/submitReview.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userID = (int) session.getAttribute("user_id");
        int productID = Integer.parseInt(request.getParameter("productID"));
        String storeLocation = request.getParameter("storeLocation");
        int reviewRating = Integer.parseInt(request.getParameter("rating"));
        String reviewText = request.getParameter("reviewText");

        Product product = null;
        try {
            product = productService.getProductbyId(productID);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String productName = product.getName();
        String productCategory = product.getCategory().toString();
        double productPrice = product.getPrice();
        String userName = user.getName();

        request.setAttribute("productID", product.getId());
        request.setAttribute("productName", productName);
        request.setAttribute("productCategory", productCategory);
        request.setAttribute("productPrice", productPrice);

        ReviewService reviewService = new ReviewService();
        reviewService.submitReview(product.getId(), productName, productCategory, productPrice, storeLocation, userID,
                userName,
                reviewRating,
                reviewText);

        String embedding = reviewService.generateEmbedding(reviewText);
        try {
            reviewService.saveReviewEmbeddingToDatabase(productID, embedding);
            System.out.println("Saved Embedding for productID: " + productID);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("index.jsp?message=Review submitted successfully!");
    }

}
