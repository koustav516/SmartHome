package com.smarthome.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.smarthome.models.User;
import com.smarthome.utils.DbConnection;

public class UserService {

	public boolean userRegistration(User user) {

		boolean result = false;

		String sql = "INSERT INTO Users (name, email, password, role) VALUES (?, ?, ?, ?)";

		try (Connection conn = DbConnection.initializeDb();
				PreparedStatement statement = conn.prepareStatement(sql)) {

			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getRole());

			int rowsAffected = statement.executeUpdate();
			result = (rowsAffected > 0);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}

	public User authenticateUser(String email, String password) {
		User user = null;

		try (Connection conn = DbConnection.initializeDb()) {

			String sql = "SELECT * FROM Users WHERE email = ? AND password= ?;";

			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, email);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return user;

	}

	public User getUserById(int id) throws ClassNotFoundException, SQLException {
		User user = null;

		try (Connection con = DbConnection.initializeDb()) {
			String sql = "SELECT * FROM Users WHERE id = ?";
			PreparedStatement statement = con.prepareStatement(sql);

			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
			}
		}
		return user;
	}
}
