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
                    var name = $("input[name=name]").val();
                    var fee = $("input[name=fee]").val();
                    var prerequisites = $("input[name=prerequisites]").val();

                    $.ajax({
                        type: 'POST',
                        url: '${hostURL}/admin/Course/Add',
                        dataType: 'html',
                        data: { name : name, fee : fee,  prerequisites : prerequisites},
                        success: function(data) {
                            $('#result').html(data);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h1>Add Course</h1>
        <form action="${hostURL}/admin/Course/Add" method="POST">
            <p>Name: <input type="text" name="name"/></p>
            <p>Fee <input type="text" name="fee"/></p>
            <p>Prerequisites: <input type="text" name="prerequisites"/></p>
            <p><input type="button" id="btnAdd" value="Add"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
