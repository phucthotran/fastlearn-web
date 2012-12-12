<%-- 
    Document   : sendFeedback
    Created on : Nov 30, 2012, 1:24:16 PM
    Author     : ITEXPLORE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Send Feedback</title>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnSend').click(function(){
                    var studentid = $("input[name=studentID]").val();
                      var fbtext = $("input[name=feedbackText]").val();

                    $.ajax({
                        type: 'POST',
                        url: 'SendFeedback',
                        dataType: 'html',
                        data: { studentID : studentid, feedbackText : fbtext },
                        success: function(data) {
                            $('#result').html(data);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h3>Send Feedback</h3>
        <form action="SendFeedback" method="POST">
            <p>Student ID: <input type="text" name="studentID"/></p>
            <p>Feedback Text: <input type="text" name="feedbackText"/></p>
            <p><input type="button" id="btnSend" value="Send"/></p>
            <div id="result"></div>
        </form>
    </body>
</html>
