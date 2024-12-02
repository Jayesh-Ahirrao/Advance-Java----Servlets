<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome using JSP</title>
</head>
<body>
	<%--This is comment and not transpiled and below we writing some texts --%>
	Welcome: using template text
	<h1>Welcome: using html tag</h1>
	<!--This is html comment- it is transpiled and also send over network-->
	<%-- Scriptfull Elements --%>
	<%-- 1. Scriplet --%>
	<% out.print("Welcome: using scriplet\n"); %> <%--out is implicite object in JSP --%>
	<br/>
	<%="Welcome: Using expression\n" %>
	<%--This is Declaration element which declares the field or method at class level scope so common and accessible, placed outside the service method --%>
	<%! int n = 10; %> 
	<%=++n %>
</body>
</html>