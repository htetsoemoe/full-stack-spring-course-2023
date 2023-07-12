<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="label" required="true" %>

<div class="mb-3">
	<label class="form-label">${ label }</label>
	<jsp:doBody></jsp:doBody>
</div>