<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${categoryName}forSale</title>
<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>

	<div class="container">
		<h1>${categoryName} Products</h1>

		<table class="product-table" border="1">
			<thead>
				<tr>
					<th>Product Name</th>
					<th>Description</th>
					<th>Category</th>
					<th>Price</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${products}">
					<tr>
						<td>${product.name}</td>
						<td>${product.desc}</td>
						<td>${product.category}</td>
						<td>$${product.price}</td>
						<td><form action="addToCart" method="post">
								<input type="hidden" name="productID" value="${product.id}" />
								<button type="submit" class="add-to-cart-btn">
									<i class="fas fa-shopping-cart"></i> Add to Cart </button>
							</form></td>
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

				<c:if test="${empty products}">
					<tr>
						<td colspan="5">No products found!</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<a href="index.jsp">Back to Home</a>
</body>
</html>