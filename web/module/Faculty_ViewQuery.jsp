<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="q" value="${query}"></c:set>
<div id="viewQueryBlock" class="transparentBlock center">
    <h5 class="blockTitle">Chi Tiết Vấn Đáp</h5>
    <div class="blockContent">
        <h6>${q.title}</h6>
        <span class="sendBy">Gởi Bởi Sinh Viên: ${q.queryDetails.student.name}</span>
        <form id="fResponseQuery" class="queryContent">
            <input type="hidden" value="${q.queryID}" name="queryID"/>
            <p class="received">${q.queryDetails.queryText} (${q.queryDetails.dateOfQuery})</p>
            <c:if test="${q.queryDetails.responseText != null}">
                <p class="sent">${q.queryDetails.responseText} ${q.queryDetails.dateOfResponse}</p>
            </c:if>
            <c:if test="${q.queryDetails.responseText == null}">
                <p><textarea class="solidTextarea" type="text" name="responseText"></textarea><br/><br/>
                <input id="btnResponse" class="redButton" type="button" value="Trả Lời"/></p>
            </c:if>
            <div id="reponseResult"></div>
        </form>
    </div>
</div>