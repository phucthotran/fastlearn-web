<%-- 
    Document   : Faculty
    Created on : Dec 10, 2012, 6:18:59 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giảng Viên</title>
        <link rel="stylesheet" type="text/css" href="${hostURL}/skin/css/faculty.css"/>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript" src="${hostURL}/js/events.js"></script>
        <script type="text/javascript" src="${hostURL}/js/functions.js"></script>
        <script type="text/javascript">
            $(function(){

                $('#btnResponse').click(function(){
                    var queryid = $("#queryid").val();
                    var facultyid = $("#facultyid").val();
                    var responsetext = $("#responseText").val();

                    $.ajax({
                        type: 'POST',
                        url: '${hostURL}/Faculty/Query/ResponseAction',
                        dataType: 'html',
                        data: { queryID : queryid, facultyID : facultyid, responseText : responsetext },
                        success: function(data) {
                            $('#reponseResult').html(data);
                        }
                    });
                });

            });
        </script>
    </head>
    <body>
        <c:set var="f" value="${faculty}"></c:set>
        <input type="hidden" value="${f.facultyID}" id="facultyid"/>
        <jsp:include page="module/Faculty_QueryNotification.jsp"></jsp:include>
        <jsp:include page="module/Navigation.jsp"></jsp:include>
        <jsp:include page="module/Notification.jsp"></jsp:include>
        <h5 id="userBlock">${f.name}</h5>
        <div id="facultyBlock" class="transparentBlock wrappCenter">
            <h4 class="blockTitle">Giảng Viên</h4>
            <c:set var="view" value="${VIEWTYPE}"></c:set>
            <c:if test="${view == 'FULL'}">
                <jsp:include page="module/CourseMaterial.jsp"></jsp:include>
                <jsp:include page="module/Faculty_QueryManage.jsp"></jsp:include>
                <jsp:include page="module/ChangePassword.jsp"></jsp:include>
            </c:if>
            <c:if test="${view == 'QUERY'}">
                <jsp:include page="module/Faculty_ViewQuery.jsp"></jsp:include>
            </c:if>
        </div> <!-- END DIV#FacultyBlock -->
    </body>
</html>
