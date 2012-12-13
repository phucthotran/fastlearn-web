<%-- 
    Document   : PostMessage
    Created on : Dec 8, 2012, 6:08:41 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Message</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript">
            $(function(){
                $('#btnPost').click(function(){
                    var title = $("input[name=title]").val();
                    var message = $("input[name=message]").val();
                    var m_type = $("select[name=type]").val();

                    $.ajax({
                        type: 'POST',
                        url: '${hostURL}/admin/Message/PostAction',
                        dataType: 'html',
                        data: { title : title, message : message, type : m_type },
                        success: function(data) {
                            $('#result').html(data);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <h1>Post Message</h1>
        <p>Title: <input type="text" name="title"/></p>
        <p>Message: <input type="text" name="message"/></p>
        <p>Message for:
            <select name="type">
                <option value="1">All</option>
                <option value="2">Student</option>
                <option value="3">Faculty</option>
            </select>
        </p>
        <p><input type="button" id="btnPost" value="Post"/></p>
        <div id="result">
        </div>
    </body>
</html>
