<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="q" value="${query}"></c:set>
<div id="viewQueryBlock" class="transparentBlock center">
    <h5 class="blockTitle">Chi Tiết Vấn Đáp</h5>
    <div class="blockContent">
        <h6>${q.title}</h6>
        <span class="sendBy">Gởi Bởi Sinh Viên: ${q.student.name}</span>
        <div class="queryContent">
            <input type="hidden" value="${q.queryID}" id="queryid"/>
            <p class="received">${q.queryText} (${q.dateOfQuery})</p>
            <c:if test="${q.responseText != null}">
                <p class="sent">${q.responseText} ${q.dateOfResponse}</p>
            </c:if>
            <c:if test="${q.responseText == null}">
                <p><textarea class="solidTextarea" type="text" id="responseText"></textarea><br/><br/>
                <input id="btnResponse" class="redButton" type="button" value="Trả Lời"/></p>
            </c:if>
            <div id="reponseResult"></div>
        </div>
    </div>
</div>
