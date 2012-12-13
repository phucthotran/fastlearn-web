<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="c" value="${course}"></c:set>
<div id="viewCourseBlock" class="transparentBlock center">
    <h5 class="blockTitle">Chi Tiết Khóa Học</h5>
    <div class="blockContent">
        <h6>Khóa Học: ${c.name}</h6>
        <p>Học Phí: ${c.fee}$</p>
        <p>Yêu Cầu: ${c.prerequisites}</p>
    </div>
</div>