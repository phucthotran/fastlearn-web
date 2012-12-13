<%-- 
    Document   : studentManage
    Created on : Dec 5, 2012, 10:21:09 AM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Manage</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnFind').click(function(){
                    var findText = $("#findText").val();
                    var findType = $("#findType").val();

                    $.ajax({
                        type: 'GET',
                        url: '${hostURL}/admin/Student/Find',
                        dataType: 'html',
                        data: { findtext : findText, findtype : findType},
                        success: function(data) {
                            $('#result').html(data);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h1>Student</h1>
        <p>Find: <input type="text" id="findText"/>&nbsp;
            By
            <select id="findType">
                <option value="name">Name</option>
                <option value="email">Email</option>
                <option value="address">Address</option>
            </select>&nbsp;
            <input type="button" id="btnFind" value="Find"/>
        </p>
        <div id="result">
        </div>
        <c:forEach var="s" items="${lstStudent}">
            <li>Name: ${s.name}, Email: ${s.email}, Address: ${s.address}&nbsp;
                [<a href="${hostURL}/admin/Student/Info?id=${s.studentID}">Details</a>]
                [<a href="${hostURL}/admin/Student/Edit?id=${s.studentID}">Edit</a>]
                [<a href="${hostURL}/admin/Block?id=${s.studentID}">Block</a>]
            </li>
        </c:forEach>
    </body>
</html>
