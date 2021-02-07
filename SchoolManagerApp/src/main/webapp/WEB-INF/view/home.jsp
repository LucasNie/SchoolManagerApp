<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/view/header.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<title>School Manager</title>
		<style>
			table, th, td {
  				border: 1px solid black;
  				vertical-align: top;
  				<!-- border-collapse: collapse; -->
				}
			th, td {
 		 		padding: 5px;
				}
		</style>
	</head>
<body>

	<div id="wrapper">
		<div id="header">
			<h2>${user.firstName} ${user.lastName}</h2>
		</div>
	</div>
	<hr>
	<div id="container">
		<h3>You are logged in as a <c:if test="${user.teacher && !user.admin}">Teacher</c:if><c:if test="${user.student}">Student</c:if><c:if test="${user.admin}">Admin</c:if>.</h3>
		
			<table>
				<tbody>
					<tr>
						<td>Username</td><td>${user.username}</td>
					</tr>
					<tr>
						<td>First name</td><td>${user.firstName}</td>
					</tr>
					<tr>
						<td>Last name</td><td>${user.lastName}</td>
					</tr>
					<tr>
						<td>Email</td><td>${user.email}</td>
					</tr>
				</tbody>
			</table>
			
			<hr>
			
			<security:authorize access="hasRole('TEACHER')">
			<table style="display: inline-block;">
				<tr>
					<th>Group member</th>
				</tr>
	
				<c:forEach var="tempGroup" items="${user.grupasSorted}">
				<tr>
					<td><a href="${pageContext.request.contextPath}/group/showGroupDetails?groupId=${tempGroup.id}">${tempGroup.description}</a></td>
				</tr>
				</c:forEach>
			</table>
			
			<table style="display: inline-block;">
				<tr>
					<th>Subject member</th>
				</tr>
	
				<c:forEach var="tempSubject" items="${user.subjectsSorted}">
				<tr>
					<td><a href="${pageContext.request.contextPath}/subject/showSubjectDetails?subjectId=${tempSubject.id}">${tempSubject.title}</a></td>
				</tr>
				</c:forEach>
			</table>
			</security:authorize>
			
			<security:authorize access="hasRole('STUDENT') and !hasRole('TEACHER')">
			<table style="display: inline-block;">
				<tr>
					<th>Group member</th>
				</tr>

				<c:forEach var="tempGroup" items="${user.grupasSorted}">
				<tr>
					<td>${tempGroup.description}</td>
				</tr>
				</c:forEach>
			</table>
			
			<table style="display: inline-block;">
				<tr>
					<th>Subject member</th>
					<th>Grades</th>
				</tr>
	
				<c:forEach var="tempSubject" items="${user.subjectsSorted}">
				<tr>
					<td>${tempSubject.title}</td>
					<td>
					<c:forEach var="tempGrade" items="${user.gradesSorted}">
					<c:if test="${tempGrade.subject.id==tempSubject.id}">
					[${tempGrade.mark} ${tempGrade.comment}]
					</c:if> 
					</c:forEach>
					</td>
				</tr>
				</c:forEach>
			</table>
			</security:authorize>
			
			<br><br>
			<div style="color: green; font-weight: bold; line-height: 1.5;">
			Use the navigation panel at the top to explore the data.<br>
			Start with e.g. <span style="color: #870900">'Show students'</span> to create or delete students/teachers,
			show their details, assign to subjects/groups, grade them and more.<br>
			The number of options depends on access role. <span style="color: #870900">Admin</span> is the superior role with every option allowed.
			</div>
			
	</div>
</body>
</html>