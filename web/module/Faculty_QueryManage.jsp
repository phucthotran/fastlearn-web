<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="f" value="${faculty}"></c:set>
<div id="queryManageBlock" class="transparentBlock center">
    <h5 class="blockTitle">Vấn Đáp</h5>
    <table class="blockContent">
        <tr>
            <th>Tiêu Đề</th>
            <th>Sinh Viên</th>
            <th>Ngày</th>
            <th width="24"></th>
        </tr>
        <c:forEach var="q" items="${f.query}">
            <tr>
                <td>${q.title}</td>
                <td>${q.student.name}</td>
                <td><fmt:formatDate pattern="dd/MM/yyyy hh:mm:ss" value="${q.createdDate}"/></td>
                <td width="24"><a title="Chi tiết" class="detailsButton" target="_blank" href="${hostURL}/Faculty/Query/View?id=${q.queryID}"></a></td>                
            </tr>
        </c:forEach>
    </table>
    <div id="queryManageResult"></div>
    <div class="clear"></div>
    <ul class="navigator">
        <p><input class="redButton" type="button" value="Xem Thêm"/></p>
    </ul>
</div> <!-- END DIV#QueryManageBlock -->