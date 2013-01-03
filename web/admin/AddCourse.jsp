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
        <script type="text/javascript" src="${hostURL}/js/formValidate.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnAdd').click(function(){
                    $.post('${hostURL}/admin/Course/AddAction', $('#fAddCourse').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });

                $('#cNameBox').formValidate(5, 100, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                $('#cFeeBox').formValidate(2, 20, 'number', 'Hợp lệ', 'Không Hợp Lệ');
                $('#cPrereBox').formValidate(10, 150, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
            });
        </script>
    </head>
    <body>
        <h3>Add Course</h3>
        <form id="fAddCourse">
            <p id="cNameBox">Name: <input type="text" name="name"/><span class="message"></span></p>
            <p id="cFeeBox">Fee <input type="text" name="fee"/><span class="message"></span></p>
            <p id="cPrereBox">Prerequisites: <input type="text" name="prerequisites"/><span class="message"></span></p>
            <p><input type="button" id="btnAdd" value="Add"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
