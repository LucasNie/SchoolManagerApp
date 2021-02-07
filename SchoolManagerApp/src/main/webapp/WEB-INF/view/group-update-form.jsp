<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/view/header.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Group Form</title>
		<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/style.css" />
		  
		 <link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/student-form-style.css" />
	</head>
		<div id="wrapper">
			<div id="header">
				<h2>Group Update Form</h2>
			</div>
		</div>
		<div id="container">
			<h3>Update Group</h3>
			<form:form action="updateGroup" modelAttribute="group" method="POST">
			
			<form:hidden path="id"/>
			
			<table>
				<tbody>
					<tr>
						<td><label>Description:</label></td>
						<td><form:input path="description"/>
						<form:errors path="description" class="error"/></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Update" class="save"/></td>
					</tr>
				</tbody>
			</table>
			</form:form>
			<div style="clear: both;"></div>
			<p><a href="${pageContext.request.contextPath}/group/showGroups">Back to List</a></p>
		</div>
</html>