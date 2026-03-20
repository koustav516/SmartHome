<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ticket Confirmation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .confirmation-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
            text-align: center;
        }
        .ticket-id {
            font-weight: bold;
            font-size: 1.5em;
            color: #007bff;
        }
        .back-home {
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="container confirmation-container">
    <h2>Ticket Submitted Successfully</h2>
    <p>Thank you for submitting your issue. Your support ticket has been received.</p>
    <p>Your Ticket ID is:</p>
    <div class="ticket-id">${ticketId}</div>
    <p>Please use this ID for any future reference regarding this issue.</p>
    <div class="back-home">
        <a href="index.jsp" class="btn btn-primary">Back to Home</a>
    </div>
</div>

</body>
</html>
