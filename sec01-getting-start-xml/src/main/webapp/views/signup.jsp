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
		<h1>Customer Sign Up</h1>
		
		<c:url value="/signup" var="signUpUrl"></c:url>
		<sf:form action="${ signUpUrl }" modelAttribute="form" method="post" cssClass="w-50">
		
			<div class="mb-3">
				<label for="" class="form-label">Name</label>
				<sf:input path="name" placeholder="Enter Customer Name" cssClass="form-control"/>
				<sf:errors path="name" cssClass="text-danger"></sf:errors>
			</div>
			
			<div class="mb-3">
				<label for="" class="form-label">Email</label>
				<sf:input path="email" type="email" placeholder="Enter Customer Email" cssClass="form-control"/>
				<sf:errors path="email" cssClass="text-danger"></sf:errors>
			</div>
			
			<div class="mb-3">
				<label for="" class="form-label">Password</label>
				<sf:input path="password" type="password" placeholder="Enter Customer Password" cssClass="form-control"/>
				<sf:errors path="password" cssClass="text-danger"></sf:errors>
			</div>
			
			<div>
				<button type="submit" class="btn btn-primary">Sign Up</button>
				<a href="/" class="btn btn-primary">Home</a>
			</div>
		</sf:form>
	
	</div>
	
</body>
</html>