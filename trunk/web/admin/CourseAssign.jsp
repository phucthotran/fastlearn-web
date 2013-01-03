<%-- 
    Document   : CourseAssign
    Created on : Jan 2, 2013, 1:16:03 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assign Course For Faculty</title>        
        <script type="text/javascript">
            $(function(){
                $('#btnAssign').click(function(){
                    $.post('${hostURL}/admin/Faculty/CourseAssignAction', $('#fCourseAssign').serialize(),
                        function(data) {
                            $('#courseAssignResult').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <h3>Assign Course For Faculty</h3>
        <form id="fCourseAssign">
            <p>Faculty</p>
            <p>
                <select name="facultyID">
                    <c:forEach var="f" items="${lstFaculty}">
                        <option value="${f.facultyID}">${f.name}</option>
                    </c:forEach>
                </select>
            </p>
            <p>Course</p>
            <p>
                <select name="courseID">
                    <c:forEach var="c" items="${lstCourse}">
                        <option value="${c.courseID}">${c.name}</option>
                    </c:forEach>
                </select>
            </p>
            <p><input type="button" id="btnAssign" value="Assign"/></p>
            <div id="courseAssignResult"></div>
        </form>
    </body>
</html>
