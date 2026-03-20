<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Open Ticket</title>
    <link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
    <link href="service.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
    <div class="container">
        <h2>Open a Support Ticket</h2>
        <form action="submitTicketServlet" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="productDetails" class="form-label">Describe the issue:</label>
                <textarea class="form-control" id="productDetails" name="productDetails" rows="5" required></textarea>
                <small class="form-text">Please provide as much detail as possible.</small>
            </div>
            <div class="form-group">
                <label for="imageUpload" class="form-label">Upload an image (optional):</label>
                <input type="file" class="form-control-file" id="imageUpload" name="imageUpload" accept="image/*">
                <small class="form-text">JPEG, PNG, or GIF formats are preferred.</small>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Submit Ticket</button>
        </form>
    </div>
    
</body>
</html>
