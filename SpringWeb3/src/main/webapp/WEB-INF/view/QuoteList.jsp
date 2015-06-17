<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>See your quotes</title>
</head>
<body>

<center>  
  
<div style="color: teal;font-size: 30px">The Quotes</div>  
  
  
  
<c:if test="${!empty quotes}">  
<table border="1" bgcolor="black" width="600px">  
<tr style="background-color: teal;color: white;text-align: center;" height="40px">  
<td>Id</td>  
<td>Quote</td>  
<td>Rating</td>  
<td>Ack?</td>  
</tr>  
<c:forEach items="${quotes}" var="q">  
<tr style="background-color:white;color: black;text-align: center;" height="30px" >  
<td><c:out value="${q.id}"/></td>  
<td><c:out value="${q.quoteText}"/></td>  
<td><c:out value="${q.rating}"/></td>
<td><c:out value="${q.acknowledged}"/></td>  
</tr>  
</c:forEach>  
</table>  
</c:if>  
  
  
<a href="quoteAdd.html" >Add more quotes</a>  
</center>  


</body>
</html>