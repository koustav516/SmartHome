package com.smarthome.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smarthome.models.Store;
import com.smarthome.utils.DbConnection;

public class StoreService {

    public List<Store> getAllStores() throws ClassNotFoundException, SQLException {
        List<Store> stores = new ArrayList<>();

        try (Connection con = DbConnection.initializeDb()) {
            String query = "SELECT * FROM Stores";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Store store = new Store();
                store.setId(rs.getInt("id"));
                store.setName(rs.getString("name"));
                store.setStreet(rs.getString("street"));
                store.setCity(rs.getString("city"));
                store.setZipCode(rs.getInt("zip_code"));
                store.setState(rs.getString("state"));

                stores.add(store);
            }
        }
        return stores;
    }

}
