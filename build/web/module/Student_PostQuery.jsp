<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="s" value="${student}"></c:set>
<div id="postQueryBlock" class="transparentBlock center">
    <h5 class="blockTitle">Gửi Câu Hỏi Cho Giảng Viên</h5>
    <div class="blockContent">
        <p>Tiêu Đề</p>
        <p><input class="solidTextbox" type="text" id="queryTitle"/></p>
        <p>Nội Dung</p>
        <p><textarea class="solidTextarea" id="queryText"></textarea></p>
        <p>Giảng Viên</p>
        <p>
            <select id="facultyid">
                <c:forEach var="f" items="${lstFaculty}">
                    <option value="${f.facultyID}">${f.name}</option>
                </c:forEach>
            </select>
        </p>
        <div id="queryResult"></div>
    </div>
    <div class="clear"></div>
    <ul class="navigator">
        <p><input id="btnPost" class="redButton" type="button" value="Gửi"/></p>
    </ul>
</div> <!-- END DIV#PostQueryBlock -->
