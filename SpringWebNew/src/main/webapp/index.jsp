<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<c:url value="showMessage.html" var="messageUrl" />
		<p><a href="${messageUrl}">Hello World</a></p>
		<p><a href="quoteList.html">Quotes!</a></p>
		
	</body>
</html>
