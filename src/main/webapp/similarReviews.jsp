<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Similar Reviews</title>
    <style>
        table {
            width: 80%;
            margin: auto;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2 style="text-align: center;">Similar Reviews</h2>
    <table>
        <thead>
            <tr>
                <th>Product Name</th>
                <th>Review</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty reviewsTableData}">
                    <c:forEach var="reviewData" items="${reviewsTableData}">
                        <tr>
                            <td>${reviewData.productName}</td>
                            <td>${reviewData.reviewText}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="2" style="text-align: center;">No similar reviews found.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
