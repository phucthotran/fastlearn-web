<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="notificationBlock" class="redBlock wrappCenter">
    <h4 class="blockTitle textLeft">Thông Báo</h4>
    <div class="blockContent">
        <c:forEach var="m" items="${lstMessage}">
            <div>
                <h5>${m.title}</h5>
                <p>${m.date}</p>
                <p>${m.message}</p>
            </div>
        </c:forEach>
    </div>
    <div class="clear"></div>
    <ul class="navigator">
        <li class="next floatRight"><a href="#">Kế Tiếp</a></li>
        <li class="previous floatLeft"><a href="#">Trước</a></li>
    </ul>
</div>