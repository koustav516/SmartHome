<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sales Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript" src="dataVisualization.js"></script>
    <script type="text/javascript">
        google.charts.load('current', { packages: ['corechart'] });
        var salesData = [
            <c:forEach var="sales" items="${salesReport}">
                ['${sales.productName}', ${sales.totalSales}],
            </c:forEach>
        ];
    </script>
    <script type="text/javascript">
        google.charts.setOnLoadCallback(function() {
            drawSalesChart(salesData);
        });
    </script>
</head>
<body>
    <div class="container">
        <h2>Sales Report</h2>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="showTable" onclick="toggleVisibility()">
            <label class="form-check-label" for="showTable">Generate Table</label>
        </div>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="showChart" onclick="toggleVisibility()">
            <label class="form-check-label" for="showChart">Generate Chart</label>
        <div id="tableContainer" style="margin-top: 20px;">    
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Product Price</th>
                        <th>Number of Items Sold</th>
                        <th>Total Sales</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="report" items="${salesReport}">
                        <tr>
                            <td>${report.productName}</td>
                            <td>${report.price}</td>
                            <td>${report.itemsSold}</td>
                            <td>${report.totalSales}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="chart_div" style="width: 100%; height: 600px;"></div>
        <a href="index.jsp">Home</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
