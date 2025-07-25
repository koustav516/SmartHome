<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Order Confirmation</title>
<link rel="stylesheet" type="text/css" href="orderconfirmation.css">
</head>
<body>
	<div class="confirmation-container">
		<div class="card">
			<h1>Order Confirmation</h1>

			<c:if test="${not empty sessionScope.orderNumber}">
				<p>Thank you for your order!</p>
				<h2>
					Your Order Number: <span>${sessionScope.orderNumber}</span>
				</h2>

				<h3>Customer Information</h3>
				<ul>
					<li><strong>Name:</strong> ${sessionScope.user.name}</li>
					<li><strong>Email:</strong> ${sessionScope.user.email}</li>
					<li><strong>Delivery Option:</strong>
						${sessionScope.deliveryOption}</li>
					<c:if test="${sessionScope.deliveryOption eq 'pickup'}">
						<li><strong>Pickup Location:</strong>
							${sessionScope.storeLocation}</li>
					</c:if>
					<c:if test="${sessionScope.deliveryOption eq 'delivery'}">
						<li><strong>Delivery Address:</strong>
							${sessionScope.address}</li>
					</c:if>
				</ul>

				<h3>Order Summary</h3>
				<table border="1" cellpadding="5" cellspacing="0">
					<thead>
						<tr>
							<th>Product Name</th>
							<th>Quantity</th>
							<th>Price</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entry" items="${sessionScope.cart.products}">
							<tr>
								<td>${entry.key.name}</td>
								<td>${entry.value}</td>
								<td>$${entry.key.price}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<h4>Total Price: $${sessionScope.cart.getTotalPrice()}</h4>

				<h3>
					Order Date:
					<fmt:formatDate value="${sessionScope.orderDate}"
						pattern="yyyy-MM-dd" />
				</h3>
				<h3>
					Delivery/Pickup Date:
					<fmt:formatDate value="${sessionScope.deliveryDate}"
						pattern="yyyy-MM-dd" />
				</h3>

				<h5>Cancelation Policy:</h5>
				<p>You may cancel your order at least 5 business days before
					your expected ${sessionScope.deliveryOption == 'pickup' ? 'pickup' : 'delivery'}
					date.</p>
				<p>
					If you wish to cancel, please use your order number: <strong>${sessionScope.orderNumber}</strong>
				</p>

			</c:if>
			<c:if test="${empty sessionScope.orderNumber}">
				<p>No order found. Please try again.</p>
			</c:if>

			<a href="index.jsp">Go to Homepage</a>
		</div>
	</div>
</body>
</html>
