<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
	<body>
		<a href="${pageContext.request.contextPath}/">Home</a> | <a href="${pageContext.request.contextPath}/student/showStudents">Show students</a> | <a href="${pageContext.request.contextPath}/group/showGroups">Show groups</a> | <a href="${pageContext.request.contextPath}/subject/showSubjects">Show subjects</a> | 

		Username: <security:authentication property="principal.username"/> | 
		Roles: <security:authentication property="principal.authorities"/> | 
		
		<form:form style="display: inline-block;" action="${pageContext.request.contextPath}/logout" method="POST">
			<input type="submit" value="Logout"/>
		</form:form>
		
		<br><br>
	</body>
</html>