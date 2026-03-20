package com.smarthome.service;

import com.smarthome.models.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {

    public List<Map<String, Object>> getProductInventoryWithAccessoryCount(List<Product> allProducts) {
        List<Map<String, Object>> productInventory = new ArrayList<>();

        for (Product product : allProducts) {
            Map<String, Object> productInfo = new HashMap<>();
            productInfo.put("name", product.getName());
            productInfo.put("price", product.getPrice());
            productInfo.put("accessoryCount", product.getAccessoryCount());

            productInventory.add(productInfo);
        }

        return productInventory;
    }

}
