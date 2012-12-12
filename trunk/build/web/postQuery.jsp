<%--
    Document   : postQuery
    Created on : Nov 30, 2012, 10:27:15 AM
    Author     : ITEXPLORE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Query</title>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnPost').click(function(){
                    var studentid = $("input[name=studentID]").val();
                    var facultyid = $("input[name=facultyID]").val();
                    var querytext = $("input[name=queryText]").val();

                    $.ajax({
                        type: 'POST',
                        url: 'PostQuery',
                        dataType: 'html',
                        data: { studentID : studentid, facultyID : facultyid, queryText : querytext },
                        success: function(data) {
                            $('#result').html(data);
                        }
                    });
                });                
            });
        </script>
    </head>
    <body>
        <h3>Post Query</h3>
        <form action="PostQuery" method="POST">
            <p>Student Id: <input type="text" name="studentID"/></p>
            <p>Faculty Id: <input type="text" name="facultyID"/></p>
            <p>Query Text: <input type="text" name="queryText"/></p>
            <p><input type="button" id="btnPost" value="Post"/></p>
            <div id="result">
            </div>
        </form>
    </body>
</html>
