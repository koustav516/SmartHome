<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Trending Products</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Top 5 Products with Highest Rating</h4>
            </div>
            <div class="card-body">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Average Rating</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${topRatedProducts}">
                            <tr>
                                <td>${product._id}</td>
                                <td>${product.averageRating}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="card mt-3">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Top 5 Zipcode with most sold Products</h4>
            </div>
            <div class="card-body">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Zip Code</th>
                            <th>Number Of Orders</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="zip" items="${topZipCodes}">
                            <tr>
                                <td>${zip.zip_code}</td>
                                <td>${zip.orders_count}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="card mt-3">
            <div class="card-header bg-primary text-white">
                <h4 class="mb-0">Top 5 Products with Most reviews</h4>
            </div>
            <div class="card-body">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Number Of Reviews</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${topReviewedProducts}">
                            <tr>
                                <td>${product.productName}</td>
                                <td>${product.reviewCount}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>