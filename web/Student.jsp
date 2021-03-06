<%-- 
    Document   : Student
    Created on : Dec 10, 2012, 1:29:26 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sinh Viên</title>
        <link rel="stylesheet" type="text/css" href="${hostURL}/skin/css/student.css"/>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript" src="${hostURL}/js/formValidate.js"></script>
        <script type="text/javascript" src="${hostURL}/js/events.js"></script>
        <script type="text/javascript" src="${hostURL}/js/functions.js"></script>
        <script type="text/javascript">
            $(function(){
                //VALIDATE
                var b1 = $('#currentPwdBox').formValidate(8, 50, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                var b2 = $('#newPwdBox').formValidate(8, 50, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                var b3 = $('#newPwdAgainBox').formValidate(8, 50, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                var b4 = $('#qTitleBox').formValidate(10, 50, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                var b5 = $('#qResponseTextBox').formValidate(10, 250, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                var b6 = $('#fbTextBox').formValidate(10, 500, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');

                //POST QUERY
                $('#btnPost').click(function(){
                    $.post('${hostURL}/Student/Query/PostAction', $('#fPostQuery').serialize(),
                        function(data) {
                            $('#queryResult').html(data);
                        }
                    );
                });

                //REPLY QUERY
                $('#btnReply').click(function(){
                    var queryid = $('input[name=queryID]').val();
                    var responsetext = $('textarea[name=responseText]').val();
                    var facultyid = $('input[name=facultyID]').val();

                    $.post('${hostURL}/Student/Query/ResponseAction', { queryID : queryid, responseText : responsetext, facultyID : facultyid },
                        function(data) {
                            $('#replyResult').html(data);
                        }
                    );
                });

                //SEND FEEDBACK
                $('#btnSendFB').click(function(){
                    if(!b6)
                        return;

                    $.post('${hostURL}/Student/Feedback/PostAction', $('#fSendFB').serialize(),
                        function(data) {
                            $('#fbResult').html(data);
                        }
                    );
                });

                //SEARCH COURSE
                $('#btnSearch').click(function(){                    
                    $.post('${hostURL}/Student/FindCourseAction', $('#fSearchCourse').serialize(),
                        function(data) {
                            $('#courseResult').html(data);
                        }
                    );
                });

                //CHANGE PASSWORD
                $('#btnChangePassword').click(function(){
                    $.post('${hostURL}/User/ChangePasswordAction', $('#fChangePassword').serialize(),
                        function(data) {
                            $('#changePasswordResult').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <c:set var="s" value="${student}"></c:set>      
        <jsp:include page="module/Navigation.jsp"></jsp:include>
        <jsp:include page="module/Student_QueryNotification.jsp"></jsp:include>
        <jsp:include page="module/Notification.jsp"></jsp:include>
        <h5 id="userBlock">${s.name}</h5>
        <div id="studentBlock" class="transparentBlock wrappCenter">
            <h4 class="blockTitle">Sinh Viên</h4>
            <c:set var="view" value="${VIEWTYPE}"></c:set>
            <c:if test="${view == 'FULL'}">
                <jsp:include page="module/Student_SearchCourse.jsp"></jsp:include>
                <jsp:include page="module/Student_QueryManage.jsp"></jsp:include>
                <jsp:include page="module/ChangePassword.jsp"></jsp:include>
                <jsp:include page="module/Student_PostQuery.jsp"></jsp:include>
                <jsp:include page="module/Student_SendFB.jsp"></jsp:include>
            </c:if>
            <c:if test="${view == 'QUERY'}">
                <jsp:include page="module/Student_ViewQuery.jsp"></jsp:include>
            </c:if>
            <c:if test="${view == 'COURSE'}">
                <jsp:include page="module/Student_ViewCourse.jsp"></jsp:include>
            </c:if>
        </div> <!-- END DIV#StudentBlock -->
    </body>
</html>