<%-- 
    Document   : addCourse
    Created on : Nov 30, 2012, 5:13:26 PM
    Author     : ITEXPLORE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Course</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnAdd').click(function(){
                    $.post('${hostURL}/admin/Course/AddAction', $('#fAddCourse').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <h1>Add Course</h1>
        <form id="fAddCourse">
            <p>Name: <input type="text" name="name"/></p>
            <p>Fee <input type="text" name="fee"/></p>
            <p>Prerequisites: <input type="text" name="prerequisites"/></p>
            <p><input type="button" id="btnAdd" value="Add"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
