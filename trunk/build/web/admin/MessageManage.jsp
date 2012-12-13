<%-- 
    Document   : MessageManage
    Created on : Dec 8, 2012, 7:03:17 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Message Manage</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#include').load("${hostURL}/admin/Message/Post");
            });
        </script>
    </head>
    <body>
        <h1>Message Manage</h1>
        <div id="include"></div>
        <c:forEach var="m" items="${lstMessage}">
            <li>Title: ${m.title}, Date: ${m.date},
                For:
                <c:choose>
                    <c:when test="${m.type == 1}">All</c:when>
                    <c:when test="${m.type == 2}">Student</c:when>
                    <c:when test="${m.type == 3}">Faculty</c:when>
                </c:choose>
                &nbsp;
                [<a href="${hostURL}/admin/Message/Edit?id=${m.messageID}">Edit</a>]
                [<a href="${hostURL}/admin/Message/Remove?id=${m.messageID}">Remove</a>]
                <c:if test="${m.publish == 0}">
                    [<a href="${hostURL}/admin/Message/Publish?id=${m.messageID}">Publish</a>]
                </c:if>
                <c:if test="${m.publish == 1}">
                    [<a href="${hostURL}/admin/Message/Unpublish?id=${m.messageID}">Unpublish</a>]
                </c:if>
            </li>
        </c:forEach>
    </body>
</html>
