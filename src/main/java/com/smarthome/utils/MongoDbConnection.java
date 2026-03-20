package com.smarthome.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnection {

    private static MongoClient mongoClient = new MongoClient("localhost", 27017);
    private static MongoDatabase mongoDatabase = mongoClient.getDatabase("CustomerReviews");

    public static MongoDatabase getDatabase() {
        return mongoDatabase;
    }
}
