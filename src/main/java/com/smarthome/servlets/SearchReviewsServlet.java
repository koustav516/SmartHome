package com.smarthome.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smarthome.service.ReviewService;
import com.smarthome.utils.DbConnection;

public class SearchReviewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String queryText = request.getParameter("reviewQuery");

        ReviewService searchService = new ReviewService();
        String queryEmbedding = searchService.generateEmbedding(queryText);

        try {
            Map<Integer, List<Double>> reviewEmbeddings = searchService.fetchEmbeddings(DbConnection.initializeDb());
            List<Integer> topSimilarReviews = searchService.getTopSimilarReviews(parseEmbedding(queryEmbedding),
                    reviewEmbeddings);
            List<Document> similarReviews = searchService.fetchReviewsFromMongo(topSimilarReviews);

            List<Map<String, String>> reviewsTableData = new ArrayList<>();
            for (Document review : similarReviews) {
                Map<String, String> reviewData = new HashMap<>();
                reviewData.put("productName", review.getString("productModelName"));
                reviewData.put("reviewText", review.getString("reviewText"));
                reviewsTableData.add(reviewData);
            }

            request.setAttribute("reviewsTableData", reviewsTableData);
            request.getRequestDispatcher("similarReviews.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private List<Double> parseEmbedding(String embeddingString) {
        String cleanedString = embeddingString.replace("[", "").replace("]", "");
        String[] embeddingArray = cleanedString.split(",");
        List<Double> embeddingList = new ArrayList<>();
        for (String value : embeddingArray) {
            embeddingList.add(Double.parseDouble(value.trim()));
        }
        return embeddingList;
    }
}
