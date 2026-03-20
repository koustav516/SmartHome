<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory - Accessories</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript" src="dataVisualization.js"></script>
    <script type="text/javascript">
        google.charts.load('current', { packages: ['corechart'] });
        var productData = [
            <c:forEach var="product" items="${productData}">
                ['${product.name}', ${product.getAccessoryCount()}],
            </c:forEach>
        ];

        google.charts.setOnLoadCallback(function() {
            drawChart(productData);
        });
    </script>

</head>
<body>
    <div class="container">
        <h2>Product Inventory</h2>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="showTable" onclick="toggleVisibility()">
            <label class="form-check-label" for="showTable">Generate Table</label>
        </div>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="showChart" onclick="toggleVisibility()">
            <label class="form-check-label" for="showChart">Generate Chart</label>
        </div>
        <div id="tableContainer" style="margin-top: 20px;">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Accessory Count</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${productInventory}">
                        <tr>
                            <td>${product.name}</td>
                            <td>${product.price}</td>
                            <td>${product.accessoryCount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    
        <div id="chart_div" style="width: 100%; height: 500px;"></div>
        <a href="index.jsp">Home</a>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
