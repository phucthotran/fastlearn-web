<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ul id="queryNotification" class="collapsed">
    <div style="height: 24px; background: transparent url('${hostURL}/skin/closeNotification.png') 97% center no-repeat; cursor: pointer" title="Tắt" onclick="closeNotification()"></div>
    <c:forEach var="n" items="${lstNotification}">
        <c:forEach var="q" items="${n}">
            <a href="${hostURL}/Student/Query/View?id=${q.queryID}&markRead=${q.id}"><li>
                <p>${q.responseText}</p>
                <p><fmt:formatDate pattern="dd/MM/yyyy hh:mm:ss" value="${q.dateOfResponse}"/></p>
            </li></a>
        </c:forEach>
    </c:forEach>
    <div title="Tắt" onclick="closeNotification()" class="closeNotification"></div>
</ul>