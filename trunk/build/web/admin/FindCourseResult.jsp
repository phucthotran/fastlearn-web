<%-- 
    Document   : FindCourseResult
    Created on : Dec 6, 2012, 1:36:21 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Find Course Result</title>
    </head>
    <body>
        <h1>Find Course Result</h1>
        <c:forEach var="course" items="${lstCourse}">
            <c:set var="c" value="${course}"></c:set>
            <li>Student ID: ${c.courseID}</li>
            <li>Name: ${c.name}</li>
            <li>Fee: ${c.fee}</li>
            <li>Prerequisites: ${c.prerequisites}</li>
            <hr/>
        </c:forEach>
    </body>
</html>
