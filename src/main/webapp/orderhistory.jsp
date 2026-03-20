<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Order History</title>
<link rel="stylesheet" type="text/css" href="order.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="order-history-container">
		<h1>Your Order History</h1>

		<c:if test="${not empty sessionScope.cancelMessage}">
			<div class="alert alert-info" role="alert">
				${sessionScope.cancelMessage}</div>
			<c:remove var="cancelMessage" scope="session" />
		</c:if>

		<c:if test="${not empty orders}">
			<table class="order-history-table">
				<thead>
					<tr>
						<th>Order Number</th>
						<th>Order Date</th>
						<th>Delivery Option</th>
						<th>Store Location</th>
						<th>Address</th>
						<th>City</th>
						<th>State</th>
						<th>Zip Code</th>
						<th>Total Price</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="order" items="${orders}">
						<tr>
							<td>${order.orderNumber}</td>
							<td><fmt:formatDate value="${order.orderDate}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${order.deliveryOption}</td>
							<td>${order.storeLocation}</td>
							<td>${order.address}</td>
							<td>${order.city}</td>
							<td>${order.state}</td>
							<td>${order.zipCode}</td>
							<td>$${order.totalPrice}</td>
							<td>
								<form action="cancelOrderServlet" method="post">
									<input type="hidden" name="orderNumber"
										value="${order.orderNumber}" />
									<button type="submit" class="btn btn-danger">Cancel</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${empty orders}">
			<p>You have not placed any orders yet.</p>
		</c:if>

		<a href="index.jsp" class="button">Go to Homepage</a>
	</div>
</body>
</html>
