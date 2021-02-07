<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/view/header.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Student List</title>
		<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/style.css" />
	</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>School Manager</h2>
		</div>
	</div>
	<div id="container">
	<h3>Students</h3>
		<div id="content">
			<security:authorize access="hasRole('ADMIN')">
			<input type="button" value="Add Student" onclick="window.location.href='studentFormAdd'; return false" class="add-button"/>
			</security:authorize>
			<table>
			<tr>
				<th>Username</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<security:authorize access="hasRole('ADMIN') or hasRole('TEACHER')">
				<th>Action</th>
				</security:authorize>
			</tr>
			<c:forEach var="tempStudent" items="${students}">
			<tr>
			
				<c:url var="updateLink" value="/student/studentUpdateForm">
				<c:param name="studentId" value="${tempStudent.id}"/> 
				</c:url>
							
				<c:url var="deleteLink" value="/student/deleteStudent">
				<c:param name="studentId" value="${tempStudent.id}"/> 
				</c:url>
				
				<c:url var="showStudentDetailsLink" value="/student/showStudentDetails">
				<c:param name="studentId" value="${tempStudent.id}"/> 
				</c:url>
				
				<td>${tempStudent.username}</td>
				<td>${tempStudent.firstName}</td>
				<td>${tempStudent.lastName}</td>
				<td>${tempStudent.email}</td>
				<security:authorize access="hasRole('ADMIN') or hasRole('TEACHER')">
				<td><a href="${showStudentDetailsLink}">Details</a><security:authorize access="hasRole('ADMIN')"> | <a href="${updateLink}">Update</a> | <a href="${deleteLink}">Delete</a></security:authorize></td>
				</security:authorize>
			</tr>
			</c:forEach>
			</table>
		</div>
		
		<!-- ONLY TEACHERS -->
		<div id="container">
		<h3>Teachers</h3>
		<div id="content">
			<table>
			<tr>
				<th>Username</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<security:authorize access="hasRole('ADMIN') or hasRole('TEACHER')">
				<th>Action</th>
				</security:authorize>
			</tr>
			<c:forEach var="tempStudent" items="${teachers}">
			<tr>
			
				<c:url var="updateLink" value="/student/studentUpdateForm">
				<c:param name="studentId" value="${tempStudent.id}"/> 
				</c:url>
							
				<c:url var="deleteLink" value="/student/deleteStudent">
				<c:param name="studentId" value="${tempStudent.id}"/> 
				</c:url>
				
				<c:url var="showStudentDetailsLink" value="/student/showStudentDetails">
				<c:param name="studentId" value="${tempStudent.id}"/> 
				</c:url>
				
				<td>${tempStudent.username}</td>
				<td>${tempStudent.firstName}</td>
				<td>${tempStudent.lastName}</td>
				<td>${tempStudent.email}</td>
				<security:authorize access="hasRole('ADMIN') or hasRole('TEACHER')">
				<td><a href="${showStudentDetailsLink}">Details</a><security:authorize access="hasRole('ADMIN')"> | <a href="${updateLink}">Update</a> | <a href="${deleteLink}">Delete</a></security:authorize></td>
				</security:authorize>
			</tr>
			</c:forEach>
			</table>
		</div>
		</div>
		
		<div style="clear: both;"></div>
		<p><a href="${pageContext.request.contextPath}/">Back to List</a></p>
	</div>
</body>
</html>