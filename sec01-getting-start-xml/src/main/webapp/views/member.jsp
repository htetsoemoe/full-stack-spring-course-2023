<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
		<h1>Member Page</h1>

		<div>
			<a href="/" class="btn btn-primary">Home</a>
			
			<!-- 
				<sf:form class="d-inline-block" action="/logout" method="post">
					<button type="submit" class="btn btn-danger">Logout</button>
				</sf:form>
			 -->

			<a href="#" class="btn btn-danger" id="logoutBtn">Logout</a>
		</div>
	</div>
	
	<c:url value="/resources/js/client-logout.js" var="clientLogoutJs"></c:url>
	<script src="${ clientLogoutJs }"></script>

</body>
</html>