<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Spring Security</title>
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
<script type="text/javascript"
	src="../resources/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<div class="container mt-4">
		<h1>Hello Spring Security 6.0</h1>

		<div>
			<a href="/admin" class="btn btn-primary">Admin Home</a>
			<a href="/member" class="btn btn-secondary">Member Home</a>
			<a href="/customer" class="btn btn-secondary">Customer Home</a>
			<a href="/customer/address/edit" class="btn btn-info">Customer Address Edit</a>
			<a href="/authentication" class="btn btn-warning">Customer Login</a>
			<a href="/signup" class="btn btn-warning">Customer Sign Up</a>
		</div>
	</div>

</body>
</html>