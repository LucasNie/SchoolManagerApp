<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Login Page</title>
	</head>
	<body>
		<h3>Welcome to School Manager</h3>
		<h4>Login Page</h4>
		<form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="POST">
		
			<c:if test="${param.error != null }">
				<i style="color:red">Invalid username or password.</i>
			</c:if>
			
			<c:if test="${param.logout != null}"     >      
				<div style="color:green">You have been logged out.</div>
			</c:if> 
		
			<p>Username: <input type="text" name="username"/></p>
			<p>Password: <input type="password" name="password"/></p>
			<input type="submit" value="Log in"/>
			
		</form:form>
		
		<br><br>
		<div style="color: green; font-weight: bold; line-height: 1.5;">
		School Manager allows to manage students, teachers, subjects, groups and grades.<br>
		The User is able to log in and explore the data depending on its role.<br>
		Application distinguishes three roles: <span>STUDENT</span>, <span style="color:blue">TEACHER</span> and <span style="color:red">ADMIN</span>.<br><br>
		
		<span style="color:red">ADMIN</span> is able to:
		<ul>
		<li>create/delete students, teacher, groups and subjects</li>
		<li>edit students, teachers, groups and subjects</li>
		<li>add/remove students and teachers to/from groups and subjects</li>
		<li>grade students</li>
		</ul>
		
		<span style="color:blue">TEACHER</span> is able to:
		<ul>
		<li>add/remove students and teachers to/from groups and subjects</li>
		<li>grade students</li>
		</ul>
		
		STUDENT is able to:
		<ul>
		<li>only see lists of students/groups/subjects and its own grades</li>
		</ul>
		
		<div style="color:red">
		To see full potential of School Manager please login as a ADMIN:
		<br>[username: admin]
		<br>[password: admin]
		</div>
		
		<br>You may also log in as a student or a teacher:
		<br>Student:
		<br>[username: izbo]
		<br>[password: test123]
		
		<br>Teacher:
		<br>[username: most]
		<br>[password: test123]
		
		</div>
		
	</body>
</html>