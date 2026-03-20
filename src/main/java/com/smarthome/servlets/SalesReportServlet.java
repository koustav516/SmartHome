package com.smarthome.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smarthome.models.SalesReport;
import com.smarthome.service.SalesReportService;

public class SalesReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SalesReportService dao = new SalesReportService();
        try {
            List<SalesReport> salesReport = dao.getSalesReport();
            request.setAttribute("salesReport", salesReport);
            request.getRequestDispatcher("/salesReport.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
