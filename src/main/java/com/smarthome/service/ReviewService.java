package com.smarthome.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.smarthome.utils.DbConnection;
import com.smarthome.utils.MongoDbConnection;

public class ReviewService {

    private static final String OPENAI_API_KEY = "<API-KEY>";
    private static final String EMBEDDING_MODEL = "text-embedding-3-small";
    private static final String EMBEDDING_URL = "https://api.openai.com/v1/embeddings";

    public void submitReview(int productID, String productModelName, String productCategory, double productPrice,
            String storeLocation, int userID, String userName, int reviewRating, String reviewText) {

        MongoDatabase db = MongoDbConnection.getDatabase();
        MongoCollection<Document> reviewsCollection = db.getCollection("reviews");

        Document review = new Document("productID", productID)
                .append("productModelName", productModelName)
                .append("productCategory", productCategory)
                .append("productPrice", productPrice)
                .append("storeLocation", storeLocation)
                .append("userID", userID)
                .append("UserName", userName)
                .append("reviewRating", reviewRating)
                .append("reviewText", reviewText)
                .append("reviewDate", new Date());

        reviewsCollection.insertOne(review);
    }

    public String generateEmbedding(String reviewText) {
        try {
            URI uri = new URI(EMBEDDING_URL);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", EMBEDDING_MODEL);
            jsonBody.put("input", reviewText);

            OutputStream os = conn.getOutputStream();
            os.write(jsonBody.toString().getBytes("UTF-8"));
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream responseStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();
                JSONObject responseJson = new JSONObject(responseBuilder.toString());
                JSONArray data = responseJson.getJSONArray("data");
                String embedding = data.getJSONObject(0).getJSONArray("embedding").toString();

                return embedding;

            } else {
                System.out.println("Error Response Code: " + responseCode);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorResponse.append(line);
                }
                reader.close();
                System.out.println("Error Details: " + errorResponse.toString());
                return "Error processing request";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing request";
        }
    }

    public void saveReviewEmbeddingToDatabase(int productID, String embedding)
            throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO ReviewEmbeddings (productID, embedding) VALUES (?, ?)";
        try (Connection conn = DbConnection.initializeDb();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productID);
            stmt.setString(2, embedding);
            stmt.executeUpdate();
        }
    }

    public Map<Integer, List<Double>> fetchEmbeddings(Connection conn) throws SQLException {
        Map<Integer, List<Double>> embeddings = new HashMap<>();
        String sql = "SELECT productID, embedding FROM ReviewEmbeddings";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            Gson gson = new Gson();
            while (rs.next()) {
                int productId = rs.getInt("productID");
                @SuppressWarnings("unchecked")
                List<Double> embedding = gson.fromJson(rs.getString("embedding"), List.class);
                embeddings.put(productId, embedding);
            }
        }
        return embeddings;
    }

    public List<Integer> getTopSimilarReviews(List<Double> queryEmbedding,
            Map<Integer, List<Double>> reviewEmbeddings) {
        Map<Integer, Double> similarityScores = new HashMap<>();
        for (Map.Entry<Integer, List<Double>> entry : reviewEmbeddings.entrySet()) {
            double similarity = calculateCosineSimilarity(queryEmbedding, entry.getValue());
            similarityScores.put(entry.getKey(), similarity);
        }

        return similarityScores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }

    private double calculateCosineSimilarity(List<Double> vec1, List<Double> vec2) {
        double dotProduct = 0.0, normA = 0.0, normB = 0.0;
        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
            normA += Math.pow(vec1.get(i), 2);
            normB += Math.pow(vec2.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public List<Document> fetchReviewsFromMongo(List<Integer> reviewIds) {
        MongoDatabase database = MongoDbConnection.getDatabase();
        MongoCollection<Document> reviewsCollection = database.getCollection("reviews");
        return reviewsCollection.find(new Document("productID", new Document("$in", reviewIds)))
                .into(new ArrayList<>());
    }
}
