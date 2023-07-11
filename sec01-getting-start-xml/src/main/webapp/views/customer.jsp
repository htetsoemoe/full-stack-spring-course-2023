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
		<h1>Customer Page</h1>

		<p>${loginUser}</p>

		<div>
			<ul>
				<c:forEach items="${authorities}" var="authority">
					<li>${authority}</li>
				</c:forEach>
			</ul>
		</div>

		<div>
			<a href="/" class="btn btn-primary">Home</a>

			<sf:form class="d-inline-block" action="/logout" method="post">
				<button type="submit" class="btn btn-danger">Logout</button>
			</sf:form>
		</div>

		<div class="mt-4">
			<h3>My Address</h3>

			<c:choose>
				<c:when test="${not empty addresses}">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Building</th>
								<th>Street</th>
								<th>Township</th>
								<th>Division</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</c:when>
				
				<c:otherwise>
					<div class="alert alert-info">There is no data.</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>