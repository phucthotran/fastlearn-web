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
        <title>Đăng Nhập</title>
        <link rel="stylesheet" type="text/css" href="${hostURL}/skin/css/login.css"/>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript" src="${hostURL}/js/formValidate.js"></script>
        <script type="text/javascript">
            $(function(){

                $('#keyIdBox').formValidate(10, 10, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                $('#pwdBox').formValidate(8, 50, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');

                $('#btnLogin').click(function(){
                    $.post('${hostURL}/User/LoginAction', $('#fLogin').serialize(),
                        function(data) {                            
                            if(data.indexOf('http') == -1)
                                $('#resultLogin').html(data);
                            else
                                window.location.href = data;
                        }
                    );
                });

            });
        </script>
    </head>
    <body>
        <div id="loginBlock" class="transparentBlock center">
                <p id="resultLogin">${message}</p>
                <h4 class="blockTitle">Đăng Nhập</h4>
                <form id="fLogin" class="blockContent">
                    <p id="keyIdBox">
                        <span>Mã Code: </span> <input class="solidTextbox floatRight" type="text" name="userKeyId"/><span class="message"></span>
                    </p>
                    <p id="pwdBox"><span>Mật Khẩu: </span><input class="solidTextbox floatRight" type="password" name="password"/><span class="message"></p>
                </form>
                <div class="clear"></div>
                <ul class="navigator">
                    <p class="floatRight"><input class="redButton" id = "btnLogin" type="button" value="Login"/></p>
                </ul>
        </div>
    </body>
</html>
