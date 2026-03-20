<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Product Details</h2>

        <table class="table table-striped">
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
                            <button type="submit" class="btn btn-warning">Submit Review</button>
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
