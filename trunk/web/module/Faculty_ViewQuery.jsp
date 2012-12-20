<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="q" value="${query}"></c:set>
<div id="viewQueryBlock" class="transparentBlock center">
    <h5 class="blockTitle">Chi Tiết Vấn Đáp</h5>
    <div class="blockContent">
        <h6>${q.title}</h6>
        <span class="sendBy">Gởi Bởi Sinh Viên: ${q.student.name}</span>
        <form id="fResponseQuery" class="queryContent">
            <input type="hidden" value="${q.queryID}" name="queryID"/>
            <c:forEach var="qrd" items="${q.queryDetails}">
                <p class="received">${qrd.responseText} (${qrd.dateOfResponse})</p>
            </c:forEach>
            <p><textarea class="solidTextarea" name="responseText"></textarea><br/><br/>
            <input id="btnResponse" class="redButton" type="button" value="Trả Lời"/></p>
            <div id="reponseResult"></div>
        </form>
    </div>
</div>