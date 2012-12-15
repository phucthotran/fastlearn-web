<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul id="queryNotification" class="collapsed">
    <div style="height: 24px; background: transparent url('skin/closeNotification.png') 97% center no-repeat; cursor: pointer" title="Tắt" onclick="closeNotification()"></div>
    <c:forEach var="q" items="${lstQuery}">
        <li>
            <p>${q.query.title}</p>
            <p>${q.dateOfResponse}</p>
        </li>
    </c:forEach>
    <div title="Tắt" onclick="closeNotification()" class="closeNotification"></div>
</ul>