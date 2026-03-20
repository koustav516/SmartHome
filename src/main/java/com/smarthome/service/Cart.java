package com.smarthome.service;

import java.util.HashMap;
import java.util.Map;

import com.smarthome.models.Product;

public class Cart {

	private Map<Product, Integer> products;
	int quantity = 1;

	public Cart() {
		products = new HashMap<>();
	}

	public void addProduct(Product product) {
		if (product != null && quantity > 0) {
			if (products.containsKey(product)) {
				int existingQuantity = products.get(product);
				products.put(product, existingQuantity + quantity);
			} else {
				products.put(product, quantity);
			}
		}
	}

	public void removeProduct(Product product) {
	    if (product != null && products.containsKey(product)) {
	        int quantity = products.get(product); 
	        if (quantity > 1) {
	            products.put(product, quantity - 1);
	            System.out.println("Decreased quantity: " + (quantity - 1));
	        } else {
	            products.remove(product);
	        }
	    }
	}


	public Map<Product, Integer> getProducts() {
		return products;
	}

	public int getTotalQuantity() {
		return products.values().stream().mapToInt(Integer::intValue).sum();
	}

	public double getTotalPrice() {
		return products.entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
	}

}
