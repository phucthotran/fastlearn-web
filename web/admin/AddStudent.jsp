<%-- 
    Document   : AddStudent
    Created on : Jan 1, 2013, 10:44:30 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Student</title>
        <script type="text/javascript" src="${hostURL}/js/formValidate.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnAdd').click(function(){
                    $.post('${hostURL}/admin/Student/AddAction', $('#fAddStudent').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <h3>New Student</h3>
        <form id="fAddStudent">
            <p>Name: <input type="text" name="name"/></p>
            <p>Phone: <input type="text" name="phone"/></p>
            <p>Email: <input type="text" name="email"/></p>
            <p>Address <input type="text" name="address"/></p>
            <p><input type="button" id="btnAdd" value="Add Student"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
