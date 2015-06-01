<%@page import="com.example.service.QuoteService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add a Quote Here</title>
</head>
<body>


<center>  
  
<div style="color: teal;font-size: 30px">Add a Quote Form</div>  
  
  
  
<c:url var="saveQuoteAction" value="quoteSave.html"/>  
<form:form id="saveForm" modelAttribute="quote" method="post" action="${saveQuoteAction}">  
<table width="400px" height="150px">  
<tr>  
<td><form:label path="quoteText">Quote</form:label></td>  
<td><form:input  path="quoteText"/></td>  
</tr>  
<tr>  
<td><form:label path="rating">Rating</form:label></td>  
<td><form:select path="rating" items="${model.ratings}"></form:select></td>  
</tr>  
<tr><td></td><td>  
<input type="submit" value="Add" />  
</td></tr>  
</table>  
</form:form>  
  
  
<a href="quoteList.html" >Click Here to see all quotes</a>
  
</center>  
			<!-- div style="height: 131px; width: 446px; border: 1px solid #a1a1a1; padding: 10px 40px;  background: #ffffff; border-radius: 15px; margin: 0 auto;"/ -->
  
<div align="left" style="border: 1px solid #a1a1a1; padding: 10px 40px;  background: #ffffff; border-radius: 15px; margin: 0 auto;">
	To force errors during the processing of this form (the save and related messages sent), include the following in your quote:
	<ul>
		<li><b><%= QuoteService.FAIL_BEFORE_PRE_MESSAGE_STRING %></b> - fail before any messages or jdbc/hibernate activity</li>
		<li><b><%= QuoteService.FAIL_AFTER_PRE_MESSAGE_STRING %></b> - fail after the PRE message is sent, but before any jdbc/hibernate activity</li>		
		<li><b><%= QuoteService.FAIL_AFTER_JDBC_INSERT_STRING %></b> - fail after the PRE message and hibernate activity, but before the post message is sent.</li>
		<li><b><%= QuoteService.FAIL_AFTER_POST_MESSAGE_STRING %></b> - fail right before the end of the transaction (when both messages and hibernate are done).</li>
	</ul>
	To force errors during message acknowledgement of the new quote, include the following in your quote: 
	<ul>
		<li><b><%= QuoteService.FAIL_PRE_ACK_STRING %></b> - fail after the quote message is pulled, but before the ack column is updated via hibernate</li>
		<li><b><%= QuoteService.FAIL_POST_ACK_STRING %></b> - fail after the ack column is updated via hibernate</li>		
	</ul>
</div> 


</body>
</html>