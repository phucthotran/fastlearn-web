<%-- 
    Document   : FeedbackManage
    Created on : Dec 27, 2012, 2:10:10 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback Manage</title>
    </head>
    <body>
        <h1>Feedback Manage</h1>
        <ul>
            <c:forEach var="f" items="${lstFeedback}">
                <li>Student: ${f.student.name}, Date: ${f.date}, Text: ${f.feedbackText}</li>
            </c:forEach>
        </ul>
    </body>
</html>
