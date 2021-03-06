<%-- 
    Document   : FindStudentResult
    Created on : Dec 6, 2012, 1:19:30 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Find Student Result</title>
    </head>
    <body>
        <h1>Find Student Result</h1>

        <c:forEach var="student" items="${lstStudent}">
            <c:set var="s" value="${student}"></c:set>
            <li>Student ID: ${s.studentID}</li>
            <li>Name: ${s.name}</li>
            <li>Phone: ${s.phone}</li>
            <li>Email: ${s.email}</li>
            <li>Address: ${s.address}</li>
            <c:forEach var="sd" items="${s.studentDetails}">
                <li>Course: ${sd.course.name}</li>
                <li>Faculty: ${sd.faculty.name}</li>
                <li>Center: ${sd.center.name}</li>
            </c:forEach>
            <hr/>
        </c:forEach>
    </body>
</html>
