<%-- 
    Document   : login
    Created on : Nov 30, 2012, 10:17:53 AM
    Author     : ITEXPLORE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Account</title>
        <link rel="stylesheet" type="text/css" href="${hostURL}/skin/css/login.css"/>
        <script type="text/javascript" src="${hostURL}/js/functions.js"></script>
        <script type="text/javascript">
            $(function(){

                $('#btnLogin').click(function(){
                    $.post('/User/LoginAction', $('#frmLogin').serialize(),
                        function(data) {
                            $('#result').html(data);
                        }
                    );
                });

            });
        </script>
    </head>
    <body>
        <div id="loginBlock" class="transparentBlock center">
                <p id="result">${message}</p>
                <h4 class="blockTitle">Account Login</h4>
                <form id="frmLogin" class="blockContent">
                        <p><span>Code ID:</span> <input class="solidTextbox floatRight" type="text" name="userKeyId"/></p>
                        <p><span>Password: </span><input class="solidTextbox floatRight" type="password" name="password"/></p>
                </form>
                <div class="clear"></div>
                <ul class="navigator">
                        <p class="floatRight"><input class="redButton" id = "btnLogin" type="button" value="Login"/></p>
                </ul>
        </div>
    </body>
</html>
