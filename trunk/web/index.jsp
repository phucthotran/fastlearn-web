<%-- 
    Document   : index
    Created on : Dec 10, 2012, 1:23:47 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fast Learn</title>
        <link rel="stylesheet" type="text/css" href="${hostURL}/skin/css/home.css"/>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript" src="${hostURL}/js/events.js"></script>
    </head>
    <body>
        <div id="mainBlock">
            <div class="redBlock" id="notificationBlock">
                <h4 class="blockTitle textLeft">Thông Báo</h4>
                <div class="blockContent textLeft">
                    <c:forEach var="m" items="${lstMessage}">
                        <div>
                            <h5>${m.title}</h5>
                            <p><fmt:formatDate pattern="dd/MM/yyyy hh:mm:ss" value="${m.date}"/></p>
                            <p>${m.message}</p>
                        </div>
                    </c:forEach>
                </div>
                <div class="clear"></div>
                <ul class="navigator">
                    <li class="next floatRight"><a href="#">Kế Tiếp</a></li>
                    <li class="previous floatLeft"><a href="#">Trước</a></li>
                </ul>
            </div>
            <div class="clear"></div>
            <div class="greenBlock floatLeft" id="login">
                <a href="User/Login"><h4 class="blockTitle">Đăng Nhập</h4></a>
            </div>
            <div class="blueBlock floatLeft" id="contact">
                <a href="contact.jsp"><h4 class="blockTitle">Thông Tin Liên Hệ</h4></a>
            </div>
        </div>
    </body>
</html>
