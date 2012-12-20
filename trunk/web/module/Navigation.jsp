<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="navigatorBlock" class="brownBlock">
    <div>
        <a class="homeButton" href="index.jsp"></a>
        <h5>Trang Chủ</h5>
    </div>
    <div>
        <a class="logoutButton" href="index.jsp"></a>
        <h5>Thoát</h5>
    </div>
    <div>
        <a onclick="openNotification()" class="queryNotificationButton"><span class="queryCount">${notificationCount}</span></a>
        <h5>Nhắc Nhở</h5>
    </div>    
</div>