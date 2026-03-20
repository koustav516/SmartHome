package com.smarthome.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.smarthome.models.SalesReport;
import com.smarthome.utils.DbConnection;

public class SalesReportService {

    public List<SalesReport> getSalesReport() throws SQLException, ClassNotFoundException {
        List<SalesReport> salesReports = new ArrayList<>();

        // Database connection
        Connection conn = DbConnection.initializeDb();

        String query = "SELECT p.name AS ProductName, p.price AS ProductPrice, "
                + "SUM(od.quantity) AS NumberOfItemsSold, "
                + "SUM(od.quantity * p.price) AS TotalSales "
                + "FROM OrderDetails od "
                + "JOIN Products p ON od.product_id = p.id "
                + "GROUP BY od.product_id";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String productName = rs.getString("ProductName");
            double productPrice = rs.getDouble("ProductPrice");
            int itemsSold = rs.getInt("NumberOfItemsSold");
            double totalSales = rs.getDouble("TotalSales");

            SalesReport report = new SalesReport(productName, productPrice, itemsSold, totalSales);
            salesReports.add(report);
        }

        rs.close();
        stmt.close();
        conn.close();

        return salesReports;
    }

}
