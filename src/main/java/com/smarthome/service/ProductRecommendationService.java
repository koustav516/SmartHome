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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.smarthome.utils.DbConnection;

public class ProductRecommendationService {

    private static final String OPENAI_API_KEY = "<API-KEY>";
    private static final String EMBEDDING_MODEL = "text-embedding-3-small";
    private static final String EMBEDDING_URL = "https://api.openai.com/v1/embeddings";

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

    public void saveProductEmbeddingsToDatabase(int productId, String embedding) throws Exception {
        String insertQuery = "INSERT INTO ProductEmbeddings (productID, embedding) VALUES (?, ?) ON DUPLICATE KEY UPDATE embedding = VALUES(embedding)";

        try (Connection conn = DbConnection.initializeDb();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setInt(1, productId);
            pstmt.setString(2, embedding);

            pstmt.executeUpdate();
            System.out.println("Product embeddings saved successfully.");
        }
    }

    public Map<Integer, List<Double>> fetchProductEmbeddings(Connection conn) throws SQLException {
        String query = "SELECT productID, embedding FROM ProductEmbeddings";
        Map<Integer, List<Double>> embeddings = new HashMap<>();

        try (var stmt = conn.createStatement(); var rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int productId = rs.getInt("productID");
                List<Double> embedding = parseEmbedding(rs.getString("embedding"));
                embeddings.put(productId, embedding);
            }
        }
        return embeddings;
    }

    private List<Double> parseEmbedding(String embeddingJson) {
        JSONArray jsonArray = new JSONArray(embeddingJson);
        List<Double> embedding = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            embedding.add(jsonArray.getDouble(i));
        }
        return embedding;
    }

    public List<Integer> recommendProducts(String queryEmbedding, Map<Integer, List<Double>> productEmbeddings) {
        List<Double> queryVector = parseEmbedding(queryEmbedding);

        return productEmbeddings.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(
                        calculateCosineSimilarity(queryVector, e2.getValue()),
                        calculateCosineSimilarity(queryVector, e1.getValue())))
                .map(Map.Entry::getKey)
                .limit(5)
                .collect(Collectors.toList());
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
}
