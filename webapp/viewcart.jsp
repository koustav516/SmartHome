<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Your Shopping Cart</title>
<link rel="stylesheet" type="text/css" href="style.css">
<link rel="stylesheet" type="text/css" href="viewcart.css">
</head>
<body>


	<div class="container">
		<h1>Your Shopping Cart</h1>

		<c:choose>
			<c:when test="${not empty sessionScope.cart.products}">
				<div class="card">
					<table border="1">
						<tr>
							<th>Product Name</th>
							<th>Price</th>
							<th>Quantity</th>
							<th>Total</th>
							<th>Action</th>
						</tr>

						<c:forEach var="entry" items="${sessionScope.cart.products}">
							<c:set var="product" value="${entry.key}" />
							<c:set var="quantity" value="${entry.value}" />
							<tr>
								<td>${product.name}</td>
								<td>$${product.price}</td>
								<td>${quantity}</td>
								<td>$${product.price * quantity}</td>
								<td>
									<form action="removeCartServlet" method="post">
										<input type="hidden" name="productId" value="${product.id}">
										<input type="submit" value="Remove">
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>

					<h3>Total Quantity: ${sessionScope.cart.getTotalQuantity()}</h3>
					<h3>Total Price: $${sessionScope.cart.getTotalPrice()}</h3>

					<form action="checkout.jsp" method="get">
						<input type="submit" value="Proceed to Checkout">
					</form>
				</div>
			</c:when>
			<c:otherwise>
				<p>Your cart is empty.</p>
			</c:otherwise>
		</c:choose>

		<a href="products?category=${sessionScope.lastViewedCategory}">Continue Shopping</a>
	</div>
</body>
</html>
