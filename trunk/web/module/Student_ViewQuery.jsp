<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="q" value="${query}"></c:set>
<div id="viewQueryBlock" class="transparentBlock center">
    <h5 class="blockTitle">Chi Tiết Vấn Đáp</h5>
    <div class="blockContent">
        <h6>${q.title}</h6>
        <span class="sendTo">Gởi Tới Giảng Viên: ${q.faculty.name}</span>
        <input type="hidden" value="${q.faculty.facultyID}" name="facultyID"/>
        <div class="queryContent">
            <c:forEach var="qrd" items="${q.queryDetails}">
                <p class="sent">${qrd.responseText} (${qrd.dateOfResponse})</p>
            </c:forEach>
            <input type="hidden" value="${q.queryID}" name="queryID"/>
            <p><textarea class="solidTextarea" name="responseText"></textarea><br/><br/>
            <input id="btnReply" class="redButton" type="button" value="Trả Lời"/></p>
            <div id="replyResult"></div>
        </div>
    </div>
</div>