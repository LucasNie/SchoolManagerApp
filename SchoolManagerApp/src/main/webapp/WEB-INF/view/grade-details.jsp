<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/view/header.jsp" %>

<!DOCTYPE html>
<html>
	<head>
	
		<style>
			table, th, td {
  				border: 1px solid black;
  				<!-- border-collapse: collapse; -->
				}
			th, td {
 		 		padding: 5px;
				}
		</style>
	
		<title>Grade Details</title>

	</head>
		<div id="wrapper">
			<div id="header">
				<h2>Grade Details</h2>
			</div>
			<hr>
		</div>
		<div id="container">
			<h3>Grade:</h3>
			<h1>${grade.mark} ${grade.comment}</h1>
			<hr>
			<table>
				<tbody>
					<tr>
						<td><label>Mark:</label></td>
						<td>${grade.mark}</td>
					</tr>
					<tr>
						<td><label>Comment:</label></td>
						<td>${grade.comment}</td>
					</tr>
					<tr>
						<td><label>For student:</label></td>
						<td>${grade.student.firstName} ${grade.student.lastName} | ${grade.student.username}</td>
					</tr>
					<tr>
						<td><label>From subject:</label></td>
						<td>${grade.subject.title}</td>
					</tr>
				</tbody>
			</table>

			<hr>

			<form:form action="deleteGrade" method="POST"> 
			<input type="submit" value="Delete Grade"/>
			<input type="hidden" name="gradeId" value="${grade.id}"/>
			<input type="hidden" name="subjectId" value="${grade.subject.id}"/>
			<input type="hidden" name="studentId" value="${grade.student.id}"/>
			<input type="hidden" name="fromStudent" value="${fromStudent}"/>
			</form:form>

			<p><button type="button" name="back" onclick="history.back()">BACK</button></p>
			<br><br>
			<div style="clear: both;"></div>
			<p><a href="${pageContext.request.contextPath}/subject/showSubjects">Back to Subject List</a></p>

		</div>
</html>