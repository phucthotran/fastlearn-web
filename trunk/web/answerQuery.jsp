<%-- 
    Document   : answerQuery
    Created on : Nov 30, 2012, 12:25:45 PM
    Author     : ITEXPLORE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Answer Query</title>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnAnswer').click(function(){
                    var facultyid = $("input[name=facultyID]").val();
                    var queryid = $("input[name=queryID]").val();
                    var responsetext = $("input[name=responseText]").val();

                    $.ajax({
                        type: 'POST',
                        url: 'AnswerQuery',
                        dataType: 'html',
                        data: { facultyID : facultyid, queryID : queryid, responseText : responsetext },
                        success: function(data) {
                            $('#result').html(data);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h1>Answer Query</h1>
        <form method="POST">
            <p>Faculty Id: <input type="text" name="facultyID"/></p>
            <p>Query Id: <input type="text" name="queryID"/></p>
            <p>Response Text: <input type="text" name="responseText"/></p>
            <p><input type="button" id="btnAnswer" value="Answer"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
