<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="q" value="${query}"></c:set>
<div id="viewQueryBlock" class="transparentBlock center">
    <h5 class="blockTitle">Chi Tiết Vấn Đáp</h5>
    <div class="blockContent">
        <h6>${q.title}</h6>
        <span class="sendTo">Gởi Tới Giảng Viên: ${q.faculty.name}</span>
        <div class="queryContent">
            <p class="sent">${q.queryText} (${q.dateOfQuery})</p>
            <c:if test="${q.responseText != null}">
                <p class="received">${q.responseText} (${q.dateOfResponse})</p>
            </c:if>
        </div>
    </div>
</div>