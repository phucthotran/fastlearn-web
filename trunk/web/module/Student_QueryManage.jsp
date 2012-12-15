<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="s" value="${student}"></c:set>
<div id="queryManageBlock" class="transparentBlock center">
    <h5 class="blockTitle">Vấn Đáp</h5>
    <table class="blockContent">
        <tr>
            <th>Tiêu Đề</th>
            <th>Giảng Viên</th>
            <th>Ngày Gửi</th>
            <th width="24"></th>
            <th width="24"></th>
        </tr>
        <c:forEach var="q" items="${s.queryDetails}">
            <tr>
                <td>${q.query.title}</td>
                <td>${q.faculty.name}</td>
                <td>${q.dateOfQuery}</td>
                <td width="24"><a title="Chi tiết" class="detailsButton" target="_blank" href="Student/Query/View?id=${q.queryID}"></a></td>
                <td width="24"><span title="Xóa" class="removeButton" href="" onclick="removeQuery(${q.queryID})"></span></td>
            </tr>
        </c:forEach>
    </table>
    <div id="queryManageResult"></div>
    <div class="clear"></div>
    <ul class="navigator">
        <p><input class="redButton" type="button" value="Xem Thêm"/></p>
    </ul>
</div> <!-- END DIV#QueryManageBlock -->