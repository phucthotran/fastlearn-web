<%-- 
    Document   : editStudent.jsp
    Created on : Dec 5, 2012, 10:31:39 AM
    Author     : ITExplore
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Student</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript" src="${hostURL}/js/formValidate.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnEdit').click(function(){
                    $.post('${hostURL}/admin/Student/UpdateAction', $('#fEditStudent').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <h1>Edit Student</h1>
        <form id="fEditStudent">
            <c:set var="c" value="${student}"></c:set>
            <input type="hidden" name="studentID" value="${c.studentID}"/>
            <p><input type="text" name="name" value="${c.name}"/></p>
            <p><input type="text" name="phone" value="${c.phone}"/></p>
            <p><input type="text" name="email" value="${c.email}"/></p>
            <p><input type="text" name="address" value="${c.address}"/></p>
            <p><input type="button" id="btnEdit" value="Edit"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
