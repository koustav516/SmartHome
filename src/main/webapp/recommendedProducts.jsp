<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recommended Products</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
        .add-to-cart-btn {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 5px;
        }
        .add-to-cart-btn:hover {
            background-color: #218838;
        }
        .btn-warning {
            background-color: #ffc107;
            color: black;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 5px;
        }
        .btn-warning:hover {
            background-color: #e0a800;
        }
    </style>
</head>
<body>
    <h1>Recommended Products</h1>

    <c:choose>
        <c:when test="${not empty recommendedProducts}">
            <table>
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Description</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Add to Cart</th>
                        <th>Submit Review</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${recommendedProducts}">
                        <tr>
                            <td>${product.name}</td>
                            <td>${product.desc}</td>
                            <td>${product.category}</td>
                            <td>$${product.price}</td>
                            <td>
                                <form action="addToCart" method="post">
                                    <input type="hidden" name="productID" value="${product.id}" />
                                    <button type="submit" class="add-to-cart-btn">
                                        <i class="fas fa-shopping-cart"></i> Add to Cart
                                    </button>
                                </form>
                            </td>
                            <td>
                                <form action="submitReview" method="get" style="display: inline;">
                                    <input type="hidden" name="productID" value="${product.id}" />
                                    <input type="hidden" name="productName" value="${product.name}" />
                                    <input type="hidden" name="productCategory" value="${product.category}" />
                                    <input type="hidden" name="productPrice" value="${product.price}" />
                                    <button type="submit" class="btn btn-warning">
                                        Submit Review
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No recommendations found!</p>
        </c:otherwise>
    </c:choose>

    <a href="index.jsp">Back to Home</a>
</body>
</html>
