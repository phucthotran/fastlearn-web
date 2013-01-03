<%-- 
    Document   : ChangePassword
    Created on : Jan 2, 2013, 5:16:22 PM
    Author     : ITExplore
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <script type="text/javascript" src="${hostURL}/js/jquery.js"></script>
        <script type="text/javascript" src="${hostURL}/js/formValidate.js"></script>
        <script type="text/javascript">
            $(function(){
                //VALIDATE
                $('#currentPwdBox').formValidate(8, 50, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                $('#newPwdBox').formValidate(8, 50, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');
                $('#newPwdAgainBox').formValidate(8, 50, 'mixed', 'Hợp lệ', 'Không Hợp Lệ');

                $('#btnChangePassword').click(function(){
                    $.post('${hostURL}/User/ChangePasswordAction', $('#fChangePassword').serialize(),
                        function(data) {
                            $('#changePasswordResult').html(data);
                        }
                    );
                });
            });
        </script>
    </head>
    <body>
        <h1>Change Password</h1>
        <form id="fChangePassword">
            <p>Current Password</p>
            <p id="currentPwdBox"><input type="password" name="currentPassword"/><span class="message"></span></p>
            <p>New Password</p>
            <p id="newPwdBox"><input type="password" name="newPassword"/><span class="message"></span></p>
            <p>New Password, Again</p>
            <p id="newPwdAgainBox"><input type="password" name="newPasswordAgain"/><span class="message"></span></p>
            <p><input type="button" id="btnChangePassword" value="Save"/></p>
            <div id="changePasswordResult"></div>
        </form>
    </body>
</html>
