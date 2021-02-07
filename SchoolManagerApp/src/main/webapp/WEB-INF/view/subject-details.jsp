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
	
		<title>Subject Details</title>

	</head>
		<div id="wrapper">
			<div id="header">
				<h2>Subject Details</h2>
			</div>
			<hr>
		</div>
		<div id="container">
			<h3>Subject:</h3>
			<h1>${subject.title}</h1>
			<hr>
			<table>
				<tbody>
					<tr>
						<td><label>Title:</label></td>
						<td>${subject.title}</td>
					</tr>
				</tbody>
			</table>

			<hr>

			<table>
				<tr>
					<th align="center" colspan="6">Subject members</th>
					<th align="center" colspan="2">Grades</th>
				</tr>
	
				<c:forEach var="tempStudent" items="${students}">
				
				<c:url var="removeStudentFromSubject" value="/subject/removeStudentFromSubject">
				<c:param name="subjectId" value="${subject.id}"/> 
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
					<td><a href="${removeStudentFromSubject}">Remove from this subject</a></td>
					
					<td>
					<c:forEach var="tempGrade" items="${tempStudent.gradesSorted}">
					<c:if test="${tempGrade.subject.id==subject.id}"> <a href="${pageContext.request.contextPath}/grade/showGradeDetails?gradeId=${tempGrade.id}&fromStudent=false">[${tempGrade.mark} ${tempGrade.comment}]</a> </c:if>
					</c:forEach>
					</td>

					<td>
						<form:form action="${pageContext.request.contextPath}/grade/gradeStudent" method="GET">
							
							<select name="gradeMark">
								   <c:forEach begin="1" end="6" var="i" >
								   		<option value="${i}">${i}</option>
								   </c:forEach>
							</select>
							
							<input type="text" name="gradeComment"/>
							<input type="hidden" name="studentIdToGrade" value="${tempStudent.id}"/>
							<input type="hidden" name="subjectId" value="${subject.id}"/>
							<input type="submit" value="Add grade"/>
						</form:form>
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<hr>
			
			Add to subject:
			<form:form action="${pageContext.request.contextPath}/student/addStudentToSubject" method="GET">
    			<select name="studentId">
    					<option value="0"> -- choose student -- </option>
        			<c:forEach items="${studentsAll}" var="tempStudent">
            			<option value="${tempStudent.id}">${tempStudent.firstName} ${tempStudent.lastName}</option>
       				</c:forEach>
    			</select>
    			<br>
    			<input type="hidden" name="subjectChosen" value="${subject.id}"/>
    			<input type="hidden" name="requestFromSubjectDetails" value="true"/>
    			<input type="submit" value="Add" />
			</form:form>
			
			<p><button type="button" name="back" onclick="history.back()">BACK</button></p>
			<br><br>
			<div style="clear: both;"></div>
			<p><a href="${pageContext.request.contextPath}/subject/showSubjects">Back to Subject List</a></p>

		</div>
</html>