package com.smarthome.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.smarthome.models.ProductReviewCount;
import com.smarthome.utils.DbConnection;
import com.smarthome.utils.MongoDbConnection;

public class TrendingServlet extends HttpServlet {

    private MongoCollection<Document> reviewCollection;
    private Connection con;

    public void init() {
        MongoDatabase db = MongoDbConnection.getDatabase();
        reviewCollection = db.getCollection("reviews");
        try {
            con = DbConnection.initializeDb();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Document> topRatedProducts = getTopRatedProducts(reviewCollection);
        List<Map<String, Object>> topZipCodes = getTopZipCodes();
        List<Document> topReviwedProductsIds = getTopReviewedProductIds(reviewCollection);
        List<ProductReviewCount> topReviewedProducts = getProductDetailsFromMySQL(topReviwedProductsIds);

        request.setAttribute("topRatedProducts", topRatedProducts);
        request.setAttribute("topZipCodes", topZipCodes);
        request.setAttribute("topReviewedProducts", topReviewedProducts);

        request.getRequestDispatcher("/trending.jsp").forward(request, response);
    }

    private List<Document> getTopRatedProducts(MongoCollection<Document> reviewCollection) {
        List<Document> pipeline = Arrays.asList(
                new Document("$group", new Document("_id", "$productModelName")
                        .append("averageRating", new Document("$avg", "$reviewRating"))),
                new Document("$sort", new Document("averageRating", -1)),
                new Document("$limit", 5));

        AggregateIterable<Document> result = reviewCollection.aggregate(pipeline);

        return result.into(new ArrayList<>());
    }

    private List<Map<String, Object>> getTopZipCodes() {
        List<Map<String, Object>> topZipCodes = new ArrayList<>();
        String query = "SELECT zip_code, COUNT(order_number) AS orders_count FROM Orders GROUP BY zip_code ORDER BY orders_count DESC LIMIT 5";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("zip_code", rs.getString("zip_code"));
                row.put("orders_count", rs.getInt("orders_count"));

                topZipCodes.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topZipCodes;
    }

    private List<Document> getTopReviewedProductIds(MongoCollection<Document> reviewCollection) {
        List<Document> pipeline = Arrays.asList(
                new Document("$group", new Document("_id", "$productID")
                        .append("reviewCount", new Document("$sum", 1))),
                new Document("$sort", new Document("reviewCount", -1)),
                new Document("$limit", 5));

        AggregateIterable<Document> result = reviewCollection.aggregate(pipeline);
        return result.into(new ArrayList<>());
    }

    private List<ProductReviewCount> getProductDetailsFromMySQL(List<Document> topReviewedProductIds) {
        List<ProductReviewCount> products = new ArrayList<>();

        StringBuilder idPlaceholders = new StringBuilder();
        for (int i = 0; i < topReviewedProductIds.size(); i++) {
            idPlaceholders.append("?");
            if (i < topReviewedProductIds.size() - 1) {
                idPlaceholders.append(",");
            }
        }

        String sql = "SELECT id, name FROM products WHERE id IN (" + idPlaceholders.toString() + ")";

        try (Connection con = DbConnection.initializeDb();
                PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < topReviewedProductIds.size(); i++) {
                ps.setInt(i + 1, topReviewedProductIds.get(i).getInteger("_id"));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductReviewCount product = new ProductReviewCount();
                product.setProductId(rs.getInt("id"));
                product.setProductName(rs.getString("name"));

                for (Document doc : topReviewedProductIds) {
                    if (rs.getInt("id") == doc.getInteger("_id")) {
                        product.setReviewCount(doc.getInteger("reviewCount"));
                        break;
                    }
                }

                products.add(product);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return products;
    }

}