<%-- 
    Document   : EditMessage
    Created on : Dec 8, 2012, 6:29:14 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Message</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnEdit').click(function(){
                    $.post('${hostURL}/admin/Message/EditAction', $('#fEditMessage').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <form id="fEditMessage">
            <h1>Edit Message</h1>
            <c:set var="m" value="${message}"></c:set>
            <input type="hidden" name="messageID" value="${m.messageID}"/>
            <p>Title: <input type="text" name="title" value="${m.title}"/></p>
            <p>Message: <input type="text" name="message" value="${m.message}"/></p>
            <p>Message for:
                <select name="type">
                    <option <c:if test="${m.type == 1}">selected</c:if> value="1">All</option>
                    <option <c:if test="${m.type == 2}">selected</c:if> value="2">Student</option>
                    <option <c:if test="${m.type == 3}">selected</c:if> value="3">Faculty</option>
                </select>
            </p>
            <p><input type="button" id="btnEdit" value="Edit"/></p>
            <div id="result">
            </div>
        </form>
    </body>
</html>
