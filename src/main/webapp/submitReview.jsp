<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit a Product Review</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .form-container {
            background-color: #ffffff;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }
        .form-title {
            font-weight: bold;
            color: #343a40;
        }
        .btn-submit {
            background-color: #28a745;
            border-color: #28a745;
        }
        .btn-submit:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }
        .form-label {
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="form-container">
                    <h1 class="text-center form-title mb-4">Submit a Product Review</h1>
                    
                    <form action="submitReview" method="post">
                        <div class="mb-3">
                            <div class="form-group">
                                <input type="hidden" name="productID" value="${productID}" />
                                <label class="form-label">Product Name:</label>
                                <input type="text" class="form-control" name="productName" value="${productName}" readonly />
                            </div>
                            <div class="form-group">
                                <label class="form-label">Category:</label>
                                <input type="text" class="form-control" name="productCategory" value="${productCategory}" readonly/>
                            </div>
                            <div class="form-group">
                                <label  class="form-label">Price:</label>
                                <input type="text" class="form-control" name="productPrice" value="${productPrice}" readonly/>
                            </div>
                        </div>
                        <div class="mb-3" id="storePickupOptions">
                            <label for="storeLocation" class="form-label">Select Store Location:</label> 
                            <select class="form-select" id="storeLocation" name="storeLocation">
                                <c:forEach var="store" items="${sessionScope.storeList}">
                                    <option value="${store.name} - (${store.street}, ${store.city}, ${store.zipCode}, ${store.state})">
                                        ${store.name} - (${store.street}, ${store.city}, ${store.zipCode}, ${store.state})
                                    </option>
                                </c:forEach>
                            </select>
                            <br>
                        </div>
                        <div class="mb-3">
                            <label for="rating" class="form-label">Review Rating (1-5)</label>
                            <select class="form-select" id="rating" name="rating" required>
                                <option value="">Select a Rating</option>
                                <option value="1">1 - Poor</option>
                                <option value="2">2 - Fair</option>
                                <option value="3">3 - Good</option>
                                <option value="4">4 - Very Good</option>
                                <option value="5">5 - Excellent</option>
                            </select>
                        </div>                    

                        <div class="mb-3">
                            <label for="reviewText" class="form-label">Your Review</label>
                            <textarea class="form-control" id="reviewText" name="reviewText" rows="5" placeholder="Write your review here..." required></textarea>
                        </div>

                        <div class="text-center">
                            <button type="submit" class="btn btn-submit btn-lg">Submit Review</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
