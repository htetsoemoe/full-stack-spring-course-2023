<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="jakarta.tags.core"%>    
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
		<h1>Customer Login</h1>
		
		<c:url value="/login" var="loginUrl"></c:url>
		<sf:form action="${ loginUrl }" method="post" cssClass="w-50">
		
			<div class="mb-3">
				<label for="" class="form-label">Email</label>
				<input type="text" name="username" class="form-control" placeholder="Enter Email"/>
			</div>
			
			<div class="mb-3">
				<label for="" class="form-label">Password</label>
				<input type="password" name="password" class="form-control" placeholder="Enter Password"/>
			</div>
			
			<div>
				<button type="submit" class="btn btn-primary">Login</button>
				<a href="/" class="btn btn-primary">Home</a>
			</div>
		</sf:form>
	
	</div>
	
</body>
</html>