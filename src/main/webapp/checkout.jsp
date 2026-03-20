<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Checkout</title>
<link rel="stylesheet" type="text/css" href="checkout.css">
</head>
<body>
	<div class="checkout-card">
		<h1>Checkout</h1>

		<form action="checkoutServlet" method="post">
			<h2>Personal Information</h2>
			<br> <label for="address">Address:</label> <input type="text"
				id="address" name="address" required><br>
			<br> <label for="city">City:</label> <input type="text"
				id="city" name="city" required><br>
			<br> <label for="state">State:</label> <input type="text"
				id="state" name="state" required><br>
			<br> <label for="zipCode">Zip Code:</label> <input type="text"
				id="zipCode" name="zipCode" required><br>
			<br>
			<br> <label for="Card">Card Number</label> <input type="text"
				id="Card" name="Card" required><br>
			<br>

			<h2>Delivery Option</h2>
			<input type="radio" id="pickup" name="deliveryOption" value="pickup"
				required> <label for="pickup">Store Pickup</label><br>

			<input type="radio" id="delivery" name="deliveryOption"
				value="delivery" required> <label for="delivery">Home
				Delivery</label><br>
			<br>

			<div id="storePickupOptions" style="display: none;">
				<label for="storeLocation">Select Store Location:</label> 
				<select id="storeLocation" name="storeLocation">
					<c:forEach var="store" items="${sessionScope.storeList}">
                        <option value="${store.name} - (${store.street}, ${store.city}, ${store.zipCode}, ${store.state})">
                            ${store.name} - (${store.street}, ${store.city}, ${store.zipCode}, ${store.state})
                        </option>
                    </c:forEach>
				</select><br>
				<br>
			</div>


			<h3>Total Price: $${sessionScope.cart.getTotalPrice()}</h3>

			<input type="submit" value="Confirm Order">
		</form>
	</div>
	<script>
		const pickupRadio = document.getElementById('pickup');
		const deliveryRadio = document.getElementById('delivery');
		const storePickupOptions = document
				.getElementById('storePickupOptions');

		pickupRadio.addEventListener('change', function() {
			storePickupOptions.style.display = 'block';
		});

		deliveryRadio.addEventListener('change', function() {
			storePickupOptions.style.display = 'none';
		});
	</script>
</body>
</html>
