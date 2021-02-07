<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/view/header.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Student Form</title>
		<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/style.css" />
		  
		 <link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/student-form-style.css" />
	</head>
		<div id="wrapper">
			<div id="header">
				<h2>Student Update Form</h2>
			</div>
		</div>
		<div id="container">
			<h3>Update Student</h3>
			<form:form action="updateStudent" modelAttribute="student" method="POST">
			
			<form:hidden path="id"/>
			<form:hidden path="username"/>
			<input type="hidden" name="originalEmail" value="${originalEmail}"/>
			
			<table>
				<tbody>
					<tr>
						<td><label>Password:</label></td>
						<td><form:input type="password" path="password"/>
						<form:errors path="password" class="error"/></td>
					</tr>
					<tr>
						<td><label>First Name:</label></td>
						<td><form:input path="firstName"/>
						<form:errors path="firstName" class="error"/></td>
					</tr>
					<tr>
						<td><label>Last Name:</label></td>
						<td><form:input path="lastName"/>
						<form:errors path="lastName" class="error"/></td>
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td><form:input path="email"/>
						<form:errors path="email" class="error"/>
						
						<!-- Check for registration error -->
						<c:if test="${registrationErrorEmail != null}">
							<span class="error">${registrationErrorEmail}</span>
						</c:if>
						</td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Update" class="save"/></td>
					</tr>
				</tbody>
			</table>
			</form:form>
			<div style="clear: both;"></div>
			<p><a href="${pageContext.request.contextPath}/student/showStudents">Back to List</a></p>
		</div>
</html>