<%-- 
    Document   : CourseInfo
    Created on : Dec 6, 2012, 1:04:40 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Course Info</title>
    </head>
    <body>
        <h1>Course Info</h1>
        <c:set var="c" value="${course}"></c:set>
        <li>Course ID: ${c.courseID}</li>
        <li>Name: ${c.name}</li>
        <li>Fee ${c.fee}</li>
        <li>Prerequisites: ${c.prerequisites}</li>
    </body>
</html>
