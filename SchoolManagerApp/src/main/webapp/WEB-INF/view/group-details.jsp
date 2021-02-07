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
	
		<title>Group Details</title>

	</head>
		<div id="wrapper">
			<div id="header">
				<h2>Group Details</h2>
			</div>
			<hr>
		</div>
		<div id="container">
			<h3>Group:</h3>
			<h1>${group.description}</h1>
			<hr>
			<table>
				<tbody>
					<tr>
						<td><label>Description:</label></td>
						<td>${group.description}</td>
					</tr>
				</tbody>
			</table>

			<hr>

			<table>
				<tr>
					<th align="center" colspan="6">Group members</th>
				</tr>
	
				<c:forEach var="tempStudent" items="${students}">
				
				<c:url var="removeStudentFromGroup" value="/group/removeStudentFromGroup">
				<c:param name="groupId" value="${group.id}"/> 
				<c:param name="studentId" value="${tempStudent.id}"/> 
				</c:url>
				
				<c:url var="showStudentDetails" value="/student/showStudentDetails">
				<c:param name="studentId" value="${tempStudent.id}"/> 
				</c:url>
				
				<tr>
					<td>${tempStudent.username}</td>
					<td>${tempStudent.firstName}</td>
					<td>${tempStudent.lastName}</td>
					<td>${tempStudent.email}</td>
					<td><a href="${showStudentDetails}">Show student</a></td>
					<td><a href="${removeStudentFromGroup}">Remove from this group</a></td>
				</tr>
				</c:forEach>
			</table>
			
			<hr>
			
			Add to group:
			<form:form action="${pageContext.request.contextPath}/student/addStudentToGroup" method="GET">
    			<select name="studentId">
    					<option value="0"> -- choose student -- </option>
        			<c:forEach items="${studentsAll}" var="tempStudent">
            			<option value="${tempStudent.id}">${tempStudent.firstName} ${tempStudent.lastName}</option>
       				</c:forEach>
    			</select>
    			<br>
    			<input type="hidden" name="groupChosen" value="${group.id}"/>
    			<input type="hidden" name="requestFromGroupDetails" value="true"/>
    			<input type="submit" value="Add" />
			</form:form>

			<p><button type="button" name="back" onclick="history.back()">BACK</button></p>
			<br><br>
			<div style="clear: both;"></div>
			<p><a href="${pageContext.request.contextPath}/group/showGroups">Back to Group List</a></p>

		</div>
</html>