<%-- 
    Document   : courseManage
    Created on : Nov 30, 2012, 2:51:56 PM
    Author     : ITEXPLORE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.fastlearn.entity.Course" %>
<%@page import="com.fastlearn.controller.CourseFacadeRemote" %>
<%@page import="com.fastlearn.lib.EnterpriseBean"%>
<%@page import="java.util.List" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Course Manage</title>
    </head>
    <body>
        <h1>Course Manage</h1>
        <form action="../FindCourse" action="GET">
            <p>Course name: <input type="text" name="name"/>&nbsp;<input type="submit" value="Find"/></p>
        </form>
        <%
            CourseFacadeRemote courseCtr = (CourseFacadeRemote)EnterpriseBean.lookupBean("courseRef");
            
            List<Course> lstCourse = null;
            
            //if(request.getAttribute("findData") != null)
                //lstCourse = (List<Course>)request.getAttribute("findData");
            //else            
                lstCourse = courseCtr.findAll();
        %>
        <%        
            for(Course c : lstCourse) {
        %>
            <li>Name: <%= c.getName() %>, Fee: <%= c.getFee() %> [<a href="editCourse.jsp?id=<%= c.getCourseID() %>">Edit</a>]&nbsp;[<a href="removeCourse?id=<%= c.getCourseID() %>">Remove</a>]</li>
        <%
            }
        %>
    </body>
</html>
