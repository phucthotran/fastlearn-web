<%-- 
    Document   : EditFaculty
    Created on : Dec 27, 2012, 2:44:31 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Faculty</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnEdit').click(function(){
                    $.post('${hostURL}/admin/Faculty/UpdateAction', $('#fEditFaculty').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <h1>Edit Faculty</h1>
        <form id="fEditFaculty">
            <c:set var="f" value="${faculty}"></c:set>
            <input type="hidden" name="facultyID" value="${f.facultyID}"/>
            <p><input type="text" name="name" value="${f.name}"/></p>
            <p><input type="text" name="address" value="${f.address}"/></p>
            <p><input type="button" id="btnEdit" value="Edit"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
