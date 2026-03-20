<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Products</title>
<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

<style>
    .search-reviews {
    margin: 20px 0;
    text-align: center;
}

.search-container {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px; 
}

.search-input {
    width: 60%;
    max-width: 400px;
    padding: 10px 15px;
    font-size: 16px;
    border: 2px solid #ddd;
    border-radius: 25px;
    transition: border-color 0.3s;
}

.search-input:focus {
    border-color: #007bff; 
    outline: none;
}

.search-button {
    background-color: #007bff;
    color: #fff;
    padding: 10px 20px;
    font-size: 16px;
    border: none;
    border-radius: 25px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.search-button i {
    margin-right: 5px;
}

.search-button:hover {
    background-color: #0056b3; 
}

</style>
</head>
<body>

	<div class="container">
		<h1>All Products</h1>

        <div class="search-reviews">
            <form action="searchReviewsServlet" method="post">
                <div class="search-container">
                    <input 
                        type="text" 
                        id="reviewSearch" 
                        name="reviewQuery" 
                        placeholder="Search reviews" 
                        class="search-input" 
                    />
                    <button type="submit" class="search-button">
                        <i class="fas fa-search"></i> Search
                    </button>
                </div>
            </form>
        </div>
        

        <div class="search-reviews">
            <form action="recommendProductServlet" method="post">
                <div class="search-container">
                    <input 
                        type="text" 
                        id="recomendProduct" 
                        name="productQuery" 
                        placeholder="Search products" 
                        class="search-input" 
                    />
                    <button type="submit" class="search-button">
                        <i class="fas fa-search"></i> Search
                    </button>
                </div>
            </form>
        </div>

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
				<c:forEach var="product" items="${productList}">
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

				<c:if test="${empty productList}">
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