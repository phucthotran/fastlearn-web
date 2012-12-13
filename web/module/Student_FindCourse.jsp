<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="c" items="${lstCourse}">
    <div class="transparentBlock">
        <a target="_blank" href="Student/Course/View?id=${c.courseID}" title="Chi Tiết" class="courseDetails"></a>
        <p>Khóa Học '${c.name}' (${c.fee}$)</p>
    </div>
</c:forEach>
