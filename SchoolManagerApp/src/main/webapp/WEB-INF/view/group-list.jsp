<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/view/header.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Group List</title>
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
		<h3>Groups</h3>
		<div id="content">
			<security:authorize access="hasRole('ADMIN')">
			<input type="button" value="Add Group" onclick="window.location.href='groupFormAdd'; return false" class="add-button"/>
			</security:authorize>
			<table>
			<tr>
				<th>Description</th>
				<security:authorize access="hasRole('ADMIN') or hasRole('TEACHER')">
				<th>Action</th>
				</security:authorize>
			</tr>
			<c:forEach var="tempGroup" items="${groups}">
			<tr>
			
				<c:url var="updateLink" value="/group/groupUpdateForm">
				<c:param name="groupId" value="${tempGroup.id}"/> 
				</c:url>
							
				<c:url var="deleteLink" value="/group/deleteGroup">
				<c:param name="groupId" value="${tempGroup.id}"/> 
				</c:url>
				
				<c:url var="showGroupDetailsLink" value="/group/showGroupDetails">
				<c:param name="groupId" value="${tempGroup.id}"/> 
				</c:url>
			
				<td>${tempGroup.description}</td>
				<security:authorize access="hasRole('ADMIN') or hasRole('TEACHER')">
				<td><a href="${showGroupDetailsLink}">Details</a><security:authorize access="hasRole('ADMIN')"> | <a href="${updateLink}">Update</a> | <a href="${deleteLink}">Delete</a></security:authorize></td>
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