package com.smarthome.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.smarthome.utils.Category;

public class Product {

	private int id;
	private String name;
	private double price;
	private String desc;
	private Category category;
	private List<String> accessories;

	public Product() {

	}

	public Product(int id, String name, double price, String desc, Category category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.desc = desc;
		this.category = category;
		this.accessories = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<String> getAccessories() {
		return accessories;
	}

	public void addAccessory(String accessory) {
		accessories.add(accessory);
	}

	public void setAccessories(List<String> accessories) {
		this.accessories = accessories;
	}

	public void removeAccessory(String accessory) {
		accessories.remove(accessory);
	}

	public int getAccessoryCount() {
		return (accessories != null) ? accessories.size() : 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Product product = (Product) o;
		return id == product.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
