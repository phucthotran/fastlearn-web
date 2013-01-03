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
        <script type="text/javascript" src="${hostURL}/js/formValidate.js"></script>
        <script type="text/javascript">
            $(function(){
                //VALIDATE
                $('#mTitleBox').formValidate(3, 30, 'text', 'Hợp lệ', 'Không Hợp Lệ');
                $('#mMessage').formValidate(20, 250, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');

                $('#btnEdit').click(function(){
                    $.post('${hostURL}/admin/Message/UpdateAction', $('#fEditMessage').serialize(),
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
            <p id="mTitleBox">Title: <input type="text" name="title" value="${m.title}"/><span class="message"></span></p>
            <p id="mMessage">Message: <textarea type="text" name="message">${m.message}</textarea><span class="message"></span></p>
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
