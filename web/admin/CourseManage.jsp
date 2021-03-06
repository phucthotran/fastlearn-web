<%-- 
    Document   : courseManage
    Created on : Nov 30, 2012, 2:51:56 PM
    Author     : ITEXPLORE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Course Manage</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnFind').click(function(){
                    $.post('${hostURL}/admin/Course/FindAction', $('#fFindCourse').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <h1>Course Manage</h1>
        <jsp:include page="AddCourse.jsp"></jsp:include>
        <form id="fFindCourse">
            <p>Find: <input type="text" name="findText"/>&nbsp;
                By
                <select name="findType">
                    <option value="name">Name</option>
                </select>&nbsp;
                <input type="button" id="btnFind" value="Find"/>
            </p>
            <div id="result"></div>
        </form>
        <c:forEach var="c" items="${lstCourse}">
            <li>Name: ${c.name}, Fee: ${c.fee}&nbsp;
                [<a href="${hostURL}/admin/Course/Info?id=${c.courseID}">Details</a>]
                [<a href="${hostURL}/admin/Course/Edit?id=${c.courseID}">Edit</a>]
            </li>
        </c:forEach>
    </body>
</html>
