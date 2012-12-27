<%-- 
    Document   : FacultyInfo
    Created on : Dec 27, 2012, 2:39:18 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Faculty Info</title>
    </head>
    <body>
        <h1>Faculty Info</h1>
        <c:set var="f" value="${faculty}"></c:set>
        <li>Faculty ID: ${f.facultyID}</li>
        <li>Name: ${f.name}</li>
        <li>Address: ${f.address}</li>
        <c:forEach var="fd" items="${f.facultyDetails}">
            <li>Course: ${fd.course.name}</li>
        </c:forEach>
    </body>
</html>
