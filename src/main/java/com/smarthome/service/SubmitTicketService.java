package com.smarthome.service;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.Base64;

import java.sql.Blob;
import com.smarthome.models.Ticket;
import com.smarthome.utils.DbConnection;

public class SubmitTicketService {

    private static final String API_KEY = "<API KEY>";
    private static final String URL_STRING = "https://api.openai.com/v1/chat/completions";

    public String submitTicket(int userId, String productDetails, InputStream imageStream)
            throws ClassNotFoundException, SQLException {
        String ticketId = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
        String sql = "INSERT INTO SupportTickets (ticket_id, user_id, product_details, image) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.initializeDb();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ticketId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, productDetails);
            if (imageStream != null) {
                pstmt.setBlob(4, imageStream);
            } else {
                pstmt.setNull(4, java.sql.Types.BLOB);
            }

            pstmt.executeUpdate();
        }

        return ticketId;
    }

    public Ticket getTicketByNumber(String ticketNumber) throws ClassNotFoundException, SQLException {
        String query = "SELECT ticket_id, user_id, product_details, image FROM SupportTickets WHERE ticket_id = ?";
        Ticket ticket = null;
        Connection conn = DbConnection.initializeDb();

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, ticketNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String retrievedTicketNumber = resultSet.getString("ticket_id");
                    int userId = resultSet.getInt("user_id");
                    String productDetails = resultSet.getString("product_details");
                    Blob imageBlob = resultSet.getBlob("image");
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());

                    ticket = new Ticket(retrievedTicketNumber, userId, productDetails, imageData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticket;
    }

    public String analyzeImageWithOpenAI(InputStream imageStream, String ticketNumber, String description) {
        try {
            byte[] imageBytes = imageStream.readAllBytes();
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "gpt-4o-mini");

            JSONArray messagesArray = new JSONArray();
            JSONObject systemMessage = new JSONObject();
            JSONObject userMessage = new JSONObject();
            JSONObject imageMessage = new JSONObject();
            String systemPrompt = """
                        You are an AI agent specialized in analyzing images to determine appropriate customer service actions.
                        Based on the provided image, decide whether the package should be:
                        1. Refund Order
                        2. Replace Order
                        3. Escalate to Human Agent

                        Guidelines:
                        - If the uploaded image clearly shows significant visible physical damage (e.g., broken, cracked, or non-functional product), respond with "Replace Order".
                        - If the image shows that the customer received an entirely different product than ordered, respond with "Refund Order".
                        - If the issue is unclear in the image, or additional human judgment is required, respond with "Escalate to Human Agent".

                        Please respond with one of these actions based solely on the image contents and description.
                    """;
            systemMessage.put("role", "system");
            systemMessage.put("content", systemPrompt);
            messagesArray.put(systemMessage);

            imageMessage.put("role", "user");
            imageMessage.put("content", "data:image/png;base64," + encodedImage);
            messagesArray.put(imageMessage);

            userMessage.put("role", "user");
            userMessage.put("content", description);

            jsonBody.put("messages", messagesArray);
            jsonBody.put("max_tokens", 50);
            jsonBody.put("temperature", 0.5);

            URI uri = new URI(URL_STRING);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

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
                String decision = parseOpenAiResponse(responseJson);
                return decision;
            } else {
                InputStream errorStream = conn.getErrorStream();
                StringBuilder errorBuilder = new StringBuilder();
                if (errorStream != null) {
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorBuilder.append(errorLine);
                    }
                    errorReader.close();
                }
                System.out.println("Error Response Code: " + responseCode);
                return "Error processing request: " + errorBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing request";
        }
    }

    private String parseOpenAiResponse(JSONObject responseJson) {
        String decisionText = responseJson.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
                .getString("content").trim();
        System.out.println(decisionText);
        if (decisionText.contains("Refund")) {
            return "Refund Order";
        } else if (decisionText.contains("Replace")) {
            return "Replace Order";
        } else {
            return "Escalate to Human Agent";
        }
    }

    public void saveDecisionToDatabase(String ticketNumber, String decision)
            throws SQLException, ClassNotFoundException {
        String query = "UPDATE SupportTickets SET decision = ? WHERE ticket_id = ?";
        try (Connection conn = DbConnection.initializeDb();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, decision);
            stmt.setString(2, ticketNumber);
            stmt.executeUpdate();
        }
    }

}
