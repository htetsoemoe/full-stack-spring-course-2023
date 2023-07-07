<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
		<h1>Customer Page</h1>

		<div>
			<a href="/" class="btn btn-primary">Home</a>

			<sf:form class="d-inline-block" action="/logout" method="post">
				<button type="submit" class="btn btn-danger">Logout</button>
			</sf:form>
		</div>
	</div>

</body>
</html>