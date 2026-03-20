<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Smarthome Shop</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
    <script type="text/javascript" src="ajaxUtility.js"></script>
    <style>
        #suggestions {
    position: absolute; 
    top: 100%; 
    left: 10;
    z-index: 1000; 
    width: 20%; 
    max-height: 200px; 
    overflow-y: auto; 
    background-color: white;
    border: 1px solid #ccc; 
    border-radius: 0.25rem; 
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); 
}

.list-group-item {
    padding: 10px 15px;
    cursor: pointer; 
}

.list-group-item.active {
    background-color: #ffeb3b; 
    color: #000; 
}

.list-group-item:hover {
    background-color: #f1f1f1; 
}

    </style>
</head>
<body>
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid">
			<a class="navbar-brand" href="/index.jsp">Smarthome</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="index.jsp">Home</a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> Products </a>
						<ul class="dropdown-menu">
                            <li><a class="dropdown-item"
								href="allProducts">All Products</a></li>
							<li><a class="dropdown-item"
								href="products?category=SMART_DOORBELLS">Smart Doorbells</a></li>
							<li><a class="dropdown-item"
								href="products?category=SMART_DOORLOCKS">Smart Doorlocks</a></li>
							<li><a class="dropdown-item"
								href="products?category=SMART_SPEAKERS">Smart Speakers</a></li>
							<li><a class="dropdown-item"
								href="products?category=SMART_LIGHTINGS">Smart Lightings</a></li>
							<li><a class="dropdown-item"
								href="products?category=SMART_THERMOSTATS">Smart Thermostats</a></li>
						</ul>
                    </li>
                    <li class="nav-item"><a class="nav-link active"
						aria-current="page" href="trendingServlet">Trending</a>
                    </li>
                    <form class="d-flex" role="search">
                        <input id="searchBox" class="form-control me-2" onkeyup="autocompleteSearch()" type="search" placeholder="Search Products" aria-label="Search">
                        <div id="suggestions" class="list-group mt-2"></div>
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>

                    <c:if test="${sessionScope.user.role == 'MANAGER'}">
                        <li class="nav-item"><a class="nav-link" aria-current="page" href="inventoryServlet">Inventory</a></li>
                        <li class="nav-item"><a class="nav-link" aria-current="page" href="salesReportServlet">Sales Reports</a></li>
                    </c:if>
				</ul>

				<ul class="nav justify-content-end">
					<c:choose>
						<c:when test="${not empty sessionScope.user}">
							<li class="nav-item"><a class="nav-link" aria-current="page"
								href="#">${sessionScope.user.name}</a></li>
							<li class="nav-item"><a class="nav-link" aria-current="page"
								href="orderHistoryServlet">Orders</a></li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="customerServiceDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Customer Service
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="customerServiceDropdown">
                                    <li><a class="dropdown-item" href="openTicket.jsp">Open a Ticket</a></li>
                                    <li><a class="dropdown-item" href="ticketStatus.jsp">Status of a Ticket</a></li>
                                </ul>
                            </li>
							<li class="nav-item"><a class="nav-link" aria-current="page"
								href="logout">Logout</a></li>
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="nav-link" aria-current="page"
								href="login.jsp">Login</a></li>
							<li class="nav-item"><a class="nav-link" aria-current="page"
								href="register.jsp">Register</a></li>
						</c:otherwise>
					</c:choose>

				</ul>
			</div>
		</div>
	</nav>
	
	<div class="container mt-5">
        <div class="row justify-content-center text-center">
            <c:if test="${not empty param.message}">
                <div class="alert alert-success" id="successMessage">
                    ${param.message}
                </div>
                <script>
                    setTimeout(function() {
                        let messageElement = document.getElementById("successMessage");
                        if (messageElement) {
                            messageElement.style.display = "none";
                        }
                    }, 3000);
                </script>
            </c:if>
            <div class="col-md-8">
                <h1>Welcome to Smarthome Shop</h1>
                <p>Your one-stop shop for smart home devices</p>
                <a href="#products-section" class="btn btn-primary btn-lg">Shop Now</a>
            </div>
        </div>
    </div>
	
    <section id="products-section" class="container my-5">
        <h2 class="text-center mb-4">Featured Products</h2>
        <div class="row">

            <div class="col-md-4">
                <div class="card product-card">
                    <img src="images/close-up-hand-using-device.jpg" class="card-img-top" style="height: 200px; object-fit: cover;" alt="Smart Doorbell">
                    <div class="card-body">
                        <h5 class="card-title">Smart Doorbell</h5>
                        <p class="card-text">Stay connected to your front door from anywhere.</p>
                        <a href="products?category=SMART_DOORBELLS" class="btn btn-primary">Shop Now</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card product-card">
                    <img src="images/ivan-bandura-lZCHy8PLyyo-unsplash.jpg" class="card-img-top" style="height: 200px; object-fit: cover;" alt="Smart Speaker">
                    <div class="card-body">
                        <h5 class="card-title">Smart Speaker</h5>
                        <p class="card-text">Voice-activated, intelligent control of your home.</p>
                        <a href="products?category=SMART_SPEAKERS" class="btn btn-primary">Shop Now</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card product-card">
                    <img src="images/dan-lefebvre-RFAHj4tI37Y-unsplash.jpg" class="card-img-top" style="height: 200px; object-fit: cover;" alt="Smart Thermostat">
                    <div class="card-body">
                        <h5 class="card-title">Smart Thermostat</h5>
                        <p class="card-text">Save energy and control your home's temperature remotely.</p>
                        <a href="products?category=SMART_THERMOSTATS" class="btn btn-primary">Shop Now</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card product-card">
                    <img src="images/doorlock.jpg" class="card-img-top" style="height: 200px; object-fit: cover;" alt="Smart Doorlocks">
                    <div class="card-body">
                        <h5 class="card-title">Smart Doorlock</h5>
                        <p class="card-text">Keyless Entry Door Lock Deadbolt.</p>
                        <a href="products?category=SMART_DOORLOCKS" class="btn btn-primary">Shop Now</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card product-card">
                    <img src="images/smart_light.jpg" class="card-img-top" style="height: 200px; object-fit: cover;" alt="Smart Lightings">
                    <div class="card-body">
                        <h5 class="card-title">Smart Lightings</h5>
                        <p class="card-text">Control your lights with your phone in hand</p>
                        <a href="products?category=SMART_LIGHTINGS" class="btn btn-primary">Shop Now</a>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer class="bg-light py-4">
        <div class="container text-center">
            <p>&copy; 2024 Smarthome Shop. All rights reserved.</p>
        </div>
    </footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>