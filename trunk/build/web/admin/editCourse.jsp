<%-- 
    Document   : editCourse
    Created on : Nov 30, 2012, 4:28:56 PM
    Author     : ITEXPLORE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.fastlearn.entity.Course" %>
<%@page import="com.fastlearn.controller.CourseFacadeRemote" %>
<%@page import="com.fastlearn.lib.EnterpriseBean" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Course</title>
        <script type="text/javascript" src="../js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnEdit').click(function(){
                    var courseid = $("input[name=courseid]").val();
                    var name = $("input[name=name]").val();
                    var fee = $("input[name=fee]").val();
                    var prerequisites = $("input[name=prerequisites]").val();

                    $.ajax({
                        type: 'POST',
                        url: '../EditCourse',
                        dataType: 'html',
                        data: { courseId : courseid, name : name, fee : fee,  prerequisites : prerequisites},
                        success: function(data) {
                            $('#result').html(data);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h1>Edit Course</h1>
        <form method="POST">
            <%
                CourseFacadeRemote courseRm = (CourseFacadeRemote)EnterpriseBean.lookupBean("courseRef");
                Course c = courseRm.find(Integer.parseInt(request.getParameter("id")));
            %>
            <input type="hidden" name="courseid" value="${param["id"]}"/>
            <p>Name: <input type="text" name="name" value="<%= c.getName() %>"/></p>
            <p>Fee <input type="text" name="fee" value="<%= c.getFee() %>"/></p>
            <p>Prerequisites: <input type="text" name="prerequisites" value="<%= c.getPrerequisites() %>"/></p>
            <p><input type="button" id="btnEdit" value="Edit"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
