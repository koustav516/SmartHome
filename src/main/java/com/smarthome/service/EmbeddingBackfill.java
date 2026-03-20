package com.smarthome.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.smarthome.utils.DbConnection;
import com.smarthome.utils.MongoDbConnection;

public class EmbeddingBackfill {

    public static void main(String[] args) throws Exception {
        try {
            backfillReviewEmbeddings();

            backfillProductEmbeddings();

            System.out.println("Embedding backfill for reviews and products complete!");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void backfillReviewEmbeddings() throws SQLException, ClassNotFoundException {
        MongoDatabase mongoDatabase = MongoDbConnection.getDatabase();
        MongoCollection<Document> reviewsCollection = mongoDatabase.getCollection("reviews");
        FindIterable<Document> reviews = reviewsCollection.find();

        Connection mysqlConnection = DbConnection.initializeDb();
        ReviewService reviewService = new ReviewService();

        for (Document review : reviews) {
            int productID = review.getInteger("productID");
            String reviewText = review.getString("reviewText");

            String embedding = reviewService.generateEmbedding(reviewText);

            reviewService.saveReviewEmbeddingToDatabase(productID, embedding);

            System.out.println("Saved embedding for review of productID: " + productID);
        }

        mysqlConnection.close();
        System.out.println("Review embeddings backfill complete!");
    }

    public static void backfillProductEmbeddings() throws Exception {
        String selectQuery = "SELECT id, `desc` FROM Products";

        try (Connection conn = DbConnection.initializeDb();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectQuery)) {

            ProductRecommendationService productService = new ProductRecommendationService();

            while (rs.next()) {
                int productID = rs.getInt("id");
                String description = rs.getString("desc");

                String embedding = productService.generateEmbedding(description);

                productService.saveProductEmbeddingsToDatabase(productID, embedding);

                System.out.println("Saved embedding for productID: " + productID);
            }

        }
        System.out.println("Product embeddings backfill complete!");
    }
}
