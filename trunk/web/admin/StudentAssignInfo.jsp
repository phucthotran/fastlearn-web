<%-- 
    Document   : StudentAssignInfo
    Created on : Jan 2, 2013, 3:06:21 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Assign Info</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnAssign').click(function(){
                    $.post('${hostURL}/admin/Student/AssignInfoAction', $('#fStudentAssignInfo').serialize(),
                        function(data) {
                            $('#courseAssignResult').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <h3>Student Assign Info</h3>
        <form id="fStudentAssignInfo">
            <p>Student</p>
            <p>
                <select name="studentID">
                    <c:forEach var="s" items="${lstStudent}">
                        <option value="${s.studentID}">${s.name}</option>
                    </c:forEach>
                </select>
            </p>
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
            <p>Center</p>
            <p>
                <select name="centerID">
                    <c:forEach var="c" items="${lstCenter}">
                        <option value="${c.centerID}">${c.name}</option>
                    </c:forEach>
                </select>
            </p>
            <p><input type="button" id="btnAssign" value="Assign"/></p>
            <div id="courseAssignResult"></div>
        </form>
    </body>
</html>
