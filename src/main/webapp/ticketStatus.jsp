<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ticket Status</title>
    <link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

    <style>
    .card {
        max-width: 450px;
        margin: 0 auto;
    }

    .card-header {
        font-size: 1.25rem;
        font-weight: bold;
    }

    .form-control {
        padding: 0.75rem;
        border-radius: 0.25rem;
    }
    .back-home {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="card shadow-lg border-0">
            <div class="card-header bg-primary text-white text-center">
                <h3>Check Ticket Status</h3>
            </div>
            <div class="card-body">
                <form action="ticketStatusServlet" method="post">
                    <div class="mb-4">
                        <label for="ticketNumber" class="form-label">Enter Ticket Number:</label>
                        <input type="text" id="ticketNumber" name="ticketNumber" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Check Status</button>
                </form>
                <c:if test="${not empty decision}">
                    <div class="mt-3">
                        <h5>Decision:</h5>
                        <p>${decision}</p>
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="mt-3">
                        <p>${error}</p>
                    </div>
                </c:if>
            </div>
            <div class="card-footer text-muted text-center">
                Enter your ticket number to view the current status.
            </div>
            <div class="back-home">
                <a href="index.jsp" class="btn btn-primary">Back to Home</a>
            </div>
        </div>
    </div>
    
</body>
</html>