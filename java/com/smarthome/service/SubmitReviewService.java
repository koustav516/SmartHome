package com.smarthome.service;

import java.util.Date;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.smarthome.utils.MongoDbConnection;

public class SubmitReviewService {

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

}
