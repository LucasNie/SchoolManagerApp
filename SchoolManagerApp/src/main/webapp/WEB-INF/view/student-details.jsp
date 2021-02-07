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
  				vertical-align: top;
  				<!-- border-collapse: collapse; -->
				}
			th, td {
 		 		padding: 5px;
				}
		</style>
	
		<title>Person Details</title>

	</head>
		<div id="wrapper">
			<div id="header">
				<h2>Person Details</h2>
			</div>
			<hr>
		</div>
		<div id="container">
			<c:if test="${student.teacher}"><h3>Teacher: </h3></c:if><c:if test="${!student.teacher}"><h3>Student:</h3></c:if>
			<h1>${student.firstName} ${student.lastName}</h1>
			<hr>
			<table>
				<tbody>
					<tr>
						<td><label>Username:</label></td>
						<td>${student.username}</td>
					</tr>
					<tr>
						<td><label>First Name:</label></td>
						<td>${student.firstName}</td>
					</tr>
					<tr>
						<td><label>Last Name:</label></td>
						<td>${student.lastName}</td>
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td>${student.email}</td>
					</tr>
				</tbody>
			</table>

			<hr>

			<table style="display: inline-block;">
				<tr>
					<th>Group member</th>
				</tr>
	
				<c:forEach var="tempGroup" items="${groups}">
				<tr>
					<td><a href="${pageContext.request.contextPath}/group/showGroupDetails?groupId=${tempGroup.id}">${tempGroup.description}</a></td>
				</tr>
				</c:forEach>
			</table>
			
			
			
			<table style="display: inline-block;">
				<tr>
					<th>Subject member</th>
					<th>Grades</th>
				</tr>
	
				<c:forEach var="tempSubject" items="${subjects}">
				<tr>
					<td><a href="${pageContext.request.contextPath}/subject/showSubjectDetails?subjectId=${tempSubject.id}">${tempSubject.title}</a></td>
					<td>
					<c:forEach var="tempGrade" items="${student.gradesSorted}">
					<c:if test="${tempGrade.subject.id==tempSubject.id}"><a href="${pageContext.request.contextPath}/grade/showGradeDetails?gradeId=${tempGrade.id}&fromStudent=true">[${tempGrade.mark} ${tempGrade.comment}]</a></c:if> 
					</c:forEach>
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<hr>
			
			Add to group:
			<form:form action="addStudentToGroup" method="GET">
    			<select name="groupChosen">
    					<option value="0"> -- choose group -- </option>
        			<c:forEach items="${groupsAll}" var="tempGroup">
            			<option value="${tempGroup.id}">${tempGroup.description}</option>
       				</c:forEach>
    			</select>
    			<br>
    			<input type="hidden" name="studentId" value="${student.id}"/>
    			<input type="hidden" name="requestFromGroupDetails" value="false"/>
    			<input type="submit" value="Add" />
			</form:form>
			
			<hr>
			
			Add to subject:
			<form:form action="addStudentToSubject" method="GET">
    			<select name="subjectChosen">
    					<option value="0"> -- choose subject -- </option>
        			<c:forEach items="${subjectsAll}" var="tempSubject">
            			<option value="${tempSubject.id}">${tempSubject.title}</option>
       				</c:forEach>
    			</select>
    			<br>
    			<input type="hidden" name="studentId" value="${student.id}"/>
    			<input type="hidden" name="requestFromSubjectDetails" value="false"/>
    			<input type="submit" value="Add" />
			</form:form>
			
			<hr>
			
			<p><button type="button" name="back" onclick="history.back()">BACK</button></p>
			<br><br>
			<div style="clear: both;"></div>
			<p><a href="${pageContext.request.contextPath}/student/showStudents">Back to Student List</a></p>

		</div>
</html>