<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="changePassBlock" class="transparentBlock center">
    <h5 class="blockTitle">Đổi Mật Khẩu</h5>
    <form id="fChangePassword" class="blockContent">
        <p>Mật Khẩu Hiện Tại</p>
        <p><input class="solidTextbox" type="text" name="currentPassword"/></p>
        <p>Mật Khẩu Mới</p>
        <p><input class="solidTextbox" type="password" name="newPassword"/></p>
        <p>Xác Nhận Lại</p>
        <p><input class="solidTextbox" type="password" name="newPasswordAgain"/></p>
    </form>
    <div class="clear"></div>
    <ul class="navigator">
        <p><input id="btnChangePassword" class="redButton" type="button" value="Lưu"/></p>
    </ul>
    <div id="changePasswordResult"></div>
</div> <!-- END DIV#ChangePassBlock -->