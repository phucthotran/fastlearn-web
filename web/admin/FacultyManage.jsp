<%-- 
    Document   : FacultyManage
    Created on : Dec 27, 2012, 2:29:05 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Faculty Manage</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnFind').click(function(){
                    $.post('${hostURL}/admin/Student/FindAction', $('#fFindSudent').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <h1>Faculty Manage</h1>
        <form id="fFindFaculty">
            <p>Find: <input type="text" name="findText"/>&nbsp;
                By
                <select name="findType">
                    <option value="name">Name</option>
                    <option value="email">Email</option>
                    <option value="address">Address</option>
                </select>&nbsp;
                <input type="button" id="btnFind" value="Find"/>
            </p>
            <div id="result">
            </div>
        </form>
        <c:forEach var="f" items="${lstFaculty}">
            <li>Name: ${f.name}, Address: ${f.address}&nbsp;
                [<a href="${hostURL}/admin/Faculty/Info?id=${f.facultyID}">Details</a>]
                [<a href="${hostURL}/admin/Faculty/Edit?id=${f.facultyID}">Edit</a>]
                [<a href="${hostURL}/admin/Block?id=${f.facultyID}">Block</a>]
            </li>
        </c:forEach>
    </body>
</html>
