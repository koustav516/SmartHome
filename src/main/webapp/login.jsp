<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
	
	<form action="login" method="post" class="register-form">
		<h2>Login</h2>

		<div class="form-group">
			<label for="email">Email:</label> <input type="email" id="email"
				name="email" class="form-input" required>
		</div>

		<div class="form-group">
			<label for="password">Password:</label> <input type="password"
				id="password" name="password" class="form-input" required>
		</div>
	
		<c:if test="${not empty param.message}">
                <p style="color: red;">${param.message}</p>
        </c:if>
	
		<div class="form-group">
			<button type="submit" class="btn-submit">Login</button>
		</div>
	</form>
	
</body>
</html>