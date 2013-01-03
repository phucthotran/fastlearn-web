<%-- 
    Document   : editCourse
    Created on : Nov 30, 2012, 4:28:56 PM
    Author     : ITEXPLORE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Course</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript" src="${hostURL}/js/formValidate.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnEdit').click(function(){
                    $.post('${hostURL}/admin/Course/UpdateAction', $('#fEditCourse').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });

                //VALIDATE
                $('#cNameBox').formValidate(5, 100, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                $('#cFeeBox').formValidate(2, 20, 'number', 'Hợp lệ', 'Không Hợp Lệ');
                $('#cPrereBox').formValidate(10, 150, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
            });
        </script>
    </head>
    <body>
        <h1>Edit Course</h1>
        <form id="fEditCourse" method="POST">
            <c:set var="c" value="${course}"></c:set>
            <input type="hidden" name="courseID" value="${c.courseID}"/>
            <p id="cNameBox">Name: <input type="text" name="name" value="${c.name}"/><span class="message"></span></p>
            <p id="cFeeBox">Fee <input type="text" name="fee" value="${c.fee}"/><span class="message"></span></p>
            <p id="cPrereBox">Prerequisites: <input type="text" name="prerequisites" value="${c.prerequisites}"/><span class="message"></span></p>
            <p><input type="button" id="btnEdit" value="Edit"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
