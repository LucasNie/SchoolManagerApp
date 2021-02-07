<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/view/header.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Subject List</title>
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
		<h3>Subjects</h3>
		<div id="content">
			<security:authorize access="hasRole('ADMIN')">
			<input type="button" value="Add Subject" onclick="window.location.href='subjectFormAdd'; return false" class="add-button"/>
			</security:authorize>
			<table>
			<tr>
				<th>Title</th>
				<security:authorize access="hasRole('ADMIN') or hasRole('TEACHER')">
				<th>Action</th>
				</security:authorize>
			</tr>
			<c:forEach var="tempSubject" items="${subjects}">
			<tr>
			
				<c:url var="updateLink" value="/subject/subjectUpdateForm">
				<c:param name="subjectId" value="${tempSubject.id}"/> 
				</c:url>
							
				<c:url var="deleteLink" value="/subject/deleteSubject">
				<c:param name="subjectId" value="${tempSubject.id}"/> 
				</c:url>
				
				<c:url var="showSubjectDetailsLink" value="/subject/showSubjectDetails">
				<c:param name="subjectId" value="${tempSubject.id}"/> 
				</c:url>
			
				<td>${tempSubject.title}</td>
				<security:authorize access="hasRole('ADMIN') or hasRole('TEACHER')">
				<td><a href="${showSubjectDetailsLink}">Details</a><security:authorize access="hasRole('ADMIN')"> | <a href="${updateLink}">Update</a> | <a href="${deleteLink}">Delete</a></security:authorize></td>
				</security:authorize>
			</tr>
			</c:forEach>
			</table>
		</div>
		
		<div style="clear: both;"></div>
		<p><a href="${pageContext.request.contextPath}/">Back to List</a></p>
	</div>
</body>
</html>