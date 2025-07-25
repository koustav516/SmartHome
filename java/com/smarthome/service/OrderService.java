package com.smarthome.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.smarthome.models.Order;
import com.smarthome.utils.DbConnection;

public class OrderService {

	public String processOrder(Cart cart, String name, String email, String address, String city, String state,
			String zipCode, String deliveryOption, String storeLocation, int userId) throws ClassNotFoundException {
		String orderNumber = UUID.randomUUID().toString().toUpperCase().substring(0, 8);

		double totalPrice = cart.getTotalPrice();

		try (Connection connection = DbConnection.initializeDb()) {
			String sql = "INSERT INTO orders (order_number, user_id, order_date, delivery_option, store_location, address, city, state, zip_code, total_price) VALUES (?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, orderNumber);
				statement.setInt(2, userId);
				statement.setString(3, deliveryOption);
				if ("pickup".equals(deliveryOption)) {
					statement.setString(4, storeLocation);
				} else {
					statement.setNull(4, Types.VARCHAR);
				}
				statement.setString(5, address);
				statement.setString(6, city);
				statement.setString(7, state);
				statement.setString(8, zipCode);
				statement.setDouble(9, totalPrice);
				statement.executeUpdate();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orderNumber;
	}

	public Timestamp getOrderDate(String orderNumber) throws ClassNotFoundException {
		Timestamp orderDate = null;
		try (Connection connection = DbConnection.initializeDb()) {
			String sql = "SELECT order_date FROM Orders WHERE order_number = ?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, orderNumber);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						orderDate = resultSet.getTimestamp("order_date");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderDate;

	}

	public List<Order> getOrdersById(int userId) throws ClassNotFoundException, SQLException {
		List<Order> orders = new ArrayList<>();

		String sql = "SELECT * FROM Orders WHERE user_id= ?";

		try (Connection connection = DbConnection.initializeDb();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Order order = new Order();
					order.setOrderNumber(resultSet.getString("order_number"));
					order.setUserId(resultSet.getInt("user_id"));
					order.setOrderDate(resultSet.getTimestamp("order_date"));
					order.setDeliveryOption(resultSet.getString("delivery_option"));
					order.setStoreLocation(resultSet.getString("store_location"));
					order.setAddress(resultSet.getString("address"));
					order.setCity(resultSet.getString("city"));
					order.setState(resultSet.getString("state"));
					order.setZipCode(resultSet.getString("zip_code"));
					order.setTotalPrice(resultSet.getDouble("total_price"));
					orders.add(order);
				}
			}
		}
		
		return orders;
	}
	
	public boolean cancelOrder(String orderNumber) throws ClassNotFoundException {
        try (Connection connection = DbConnection.initializeDb()) {
            String sql = "DELETE FROM orders WHERE order_number = ? AND order_date >= DATE_SUB(NOW(), INTERVAL 5 DAY)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, orderNumber);
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
	
}
