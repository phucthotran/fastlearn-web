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
    </head>
    <body>
        <div id="loginBlock" class="transparentBlock center">
                <p>${message}</p>
                <h4 class="blockTitle">Account Login</h4>
                <div class="blockContent">
                        <p><span>Code ID:</span> <input class="solidTextbox floatRight" type="text" name="codeid"/></p>
                        <p><span>Password: </span><input class="solidTextbox floatRight" type="password" name="password"/></p>
                </div>
                <div class="clear"></div>
                <ul class="navigator">
                        <p class="floatRight"><input class="redButton" type="button" value="Login"/></p>
                </ul>
        </div>
    </body>
</html>
