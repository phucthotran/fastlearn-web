<%-- 
    Document   : studentManage
    Created on : Dec 5, 2012, 10:21:09 AM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.fastlearn.entity.Student" %>
<%@page import="com.fastlearn.controller.StudentFacadeRemote" %>
<%@page import="com.fastlearn.lib.EnterpriseBean"%>
<%@page import="java.util.List" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Manage</title>
    </head>
    <body>
        <h1>Student</h1>
        <%
            StudentFacadeRemote studentRm = (StudentFacadeRemote)EnterpriseBean.lookupBean("studentRef");

            List<Student> lstStudent = studentRm.findAll();
        %>
        <%
            for(Student c : lstStudent) {
        %>
            <li>StudentID: <%= c.getStudentID() %>, Name: <%= c.getName() %>, Email: <%= c.getEmail() %> [<a href="editStudent.jsp?id=<%= c.getStudentID() %>">Edit</a>]&nbsp;[<a href="studentDetails?id=<%= c.getStudentID() %>">Details</a>]</li>
        <%
            }
        %>
    </body>
</html>
