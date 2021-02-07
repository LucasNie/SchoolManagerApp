<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/view/header.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>

	<head>
		<title>Access Denied</title>
	</head>
	<body>
		<h2>Access Denied</h2>
		<hr>
		<p>You are not authorized to access this page.</p>
		<hr>
		<a href="${pageContext.request.contextPath}/">Back to home page</a>
	</body>
	
</html>