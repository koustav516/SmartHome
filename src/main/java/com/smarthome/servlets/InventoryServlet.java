package com.smarthome.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smarthome.models.Product;
import com.smarthome.service.InventoryService;
import com.smarthome.service.ProductService;

public class InventoryServlet extends HttpServlet {

    private ProductService productService;
    private InventoryService inventoryService;

    public void init() {
        productService = new ProductService();
        inventoryService = new InventoryService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> allProducts = productService.getAllProducts();
        List<Map<String, Object>> productInventory = inventoryService
                .getProductInventoryWithAccessoryCount(allProducts);

        request.setAttribute("productInventory", productInventory);
        request.setAttribute("productData", allProducts);

        request.getRequestDispatcher("/inventory.jsp").forward(request, response);
    }

}
