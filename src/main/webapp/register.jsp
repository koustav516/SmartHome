<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>

	<form action="register" method="post" class="register-form">
		<h2>Register</h2>

		<div class="form-group">
			<label for="name">Name:</label> <input type="text" id="name"
				name="name" class="form-input" required>
		</div>

		<div class="form-group">
			<label for="email">Email:</label> <input type="email" id="email"
				name="email" class="form-input" required>
		</div>

		<div class="form-group">
			<label for="password">Password:</label> <input type="password"
				id="password" name="password" class="form-input" required>
		</div>

		<div class="form-group">
			<label for="role">Role:</label> <select id="role" name="role"
				class="form-select">
				<option value="MANAGER">Store Manager</option>
				<option value="CUSTOMER">Customer</option>
				<option value="SALESMAN">Salesman</option>
			</select>
		</div>

		<div class="form-group">
			<button type="submit" class="btn-submit">Register</button>
		</div>
	</form>



	<!-- <h2>Register New User</h2>
	<form action="register" method="post">
		<div>
			<label for="name">Name:</label> <input type="text" id="name"
				name="name" required>
		</div>
		<div>
			<label for="email">Email:</label> <input type="email" id="email"
				name="email" required>
		</div>
		<div>
			<label for="password">Password:</label> <input type="password"
				id="password" name="password" required>
		</div>
		<div>
			<label for="role">User Type:</label> <select id="role" name="role"
				required>
				<option value="MANAGER">Store Manager</option>
				<option value="CUSTOMER">Customer</option>
				<option value="SALESMAN">Salesman</option>
			</select>
		</div>
		<button type="submit">Register</button>
	</form> -->
</body>
</html>