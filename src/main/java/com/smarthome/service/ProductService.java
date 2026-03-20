package com.smarthome.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import com.smarthome.models.Product;
import com.smarthome.utils.Category;
import com.smarthome.utils.DbConnection;

public class ProductService {

	private HashMap<Category, List<Product>> products;

	public ProductService() {
		products = new HashMap<>();

		for (Category category : Category.values()) {
			products.put(category, new ArrayList<>());
		}
	}

	public void loadProducts() throws ClassNotFoundException {
		Connection con = null;

		try {
			con = DbConnection.initializeDb();

			String query = "SELECT id, name, price, `desc`, category, accesories FROM Products;";

			try (PreparedStatement statement = con.prepareStatement(query);
					ResultSet rs = statement.executeQuery()) {

				while (rs.next()) {
					Product product = new Product();
					int id = rs.getInt("id");
					String name = rs.getString("name");
					double price = rs.getDouble("price");
					String desc = rs.getNString("desc");

					String categoryStr = rs.getString("category").toUpperCase().replace(" ", "_");
					Category category = Category.valueOf(categoryStr);

					String accessoryString = rs.getString("accesories");
					List<String> accessories = Arrays.asList(accessoryString.split(","));

					product = new Product(id, name, price, desc, category);
					product.setAccessories(accessories);
					addProduct(product);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Product getProductbyId(int id) throws ClassNotFoundException {

		for (Category category : products.keySet()) {
			List<Product> productList = products.get(category);

			for (Product product : productList) {
				if (product.getId() == id) {
					return product;
				}
			}
		}
		return null;

	}

	public Product getProductDetailsbyID(int id) throws ClassNotFoundException {
		Product product = null;
		String query = "SELECT * FROM Products WHERE id = ?";

		try (Connection con = DbConnection.initializeDb();
				PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					product = new Product();
					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					product.setDesc(rs.getString("desc"));
					String categoryStr = rs.getString("category").toUpperCase().replace(" ", "_");
					Category category = Category.valueOf(categoryStr);
					product.setCategory(category);
					product.setPrice(rs.getDouble("price"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public List<Product> getAllProducts() {
		List<Product> productList = new ArrayList<>();
		Connection con = null;

		try {
			con = DbConnection.initializeDb();
			String query = "SELECT id, name, price, `desc`, category, accesories FROM Products;";

			try (PreparedStatement statement = con.prepareStatement(query);
					ResultSet rs = statement.executeQuery()) {

				while (rs.next()) {
					Product product = new Product();
					int id = rs.getInt("id");
					String name = rs.getString("name");
					double price = rs.getDouble("price");
					String desc = rs.getNString("desc");

					String categoryStr = rs.getString("category").toUpperCase().replace(" ", "_");
					Category category = Category.valueOf(categoryStr);

					String accessoryString = rs.getString("accesories");
					List<String> accessories = Arrays.asList(accessoryString.split(","));

					product = new Product(id, name, price, desc, category);
					product.setAccessories(accessories);
					productList.add(product);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return productList;
	}

	public List<Product> getMatchingProducts(String searchTerm) throws ClassNotFoundException, SQLException {
		List<Product> products = new ArrayList<>();

		try (Connection conn = DbConnection.initializeDb()) {
			String query = "SELECT id, name, price FROM Products WHERE name LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, searchTerm + "%");

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				products.add(product);
			}
		}
		return products;
	}

	public void addProduct(Product product) {
		List<Product> productCategory = products.get(product.getCategory());
		productCategory.add(product);
	}

	public List<Product> getProductsbyCategory(Category category) {
		return products.get(category);
	}

}
