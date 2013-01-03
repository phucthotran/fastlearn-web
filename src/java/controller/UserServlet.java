package controller;

import anotation.TaskToPerform;
import com.fastlearn.controller.UsersFacadeRemote;
import com.fastlearn.entity.Users;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import model.CustomServlet;

/**
 *
 * @author ITEXPLORE
 */
@WebServlet(name="UserServlet", urlPatterns={
    "/User/Login",
    "/User/LoginAction",
    "/User/Logout",
    "/User/ChangePasswordAction",
    "/admin/User/Block", 
    "/admin/User/Unblock"
})
public class UserServlet extends CustomServlet {

    @EJB
    private UsersFacadeRemote userRm;

    private static int incorrectTimes = 0;

    @Override
    public void processRequest(){
    }

    @TaskToPerform(pathToPerform = "/User/Login", forwardPage = "../login.jsp")
    public void LoginPage(){        
    }

    @TaskToPerform(pathToPerform = "/User/Logout")
    public void LogoutAction(){
        HttpSession loginSs = getRequest().getSession();
        loginSs.invalidate();

        try {
            getResponse().sendRedirect(getRequest().getServletContext().getAttribute("hostURL").toString());
        } catch (IOException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @TaskToPerform(pathToPerform = "/User/LoginAction")
    public void LoginAction(){
        String userKey = getParams().get("userKeyId");
        String pwd = getParams().get("password");
        String loginType = null;

        if(incorrectTimes >= 5){
            Users userInfo = userRm.findByUserKeyId(userKey);
            if(userInfo != null) {
                setResponseMessage("Bạn đã nhập thông tin tài khoản sài quá 5 lần. Tài khoản của bạn đã bị khóa. Vui lòng liên hệ với trung tâm để giải quyết");
                userRm.block(userKey);
            }
            else {
                setResponseMessage("Bạn đã nhập thông tin tài khoản sài quá 5 lần");
            }
            return;
        }

        if(!userRm.userAvailable(userKey, pwd)){
            setResponseMessage("Sai thông tin đăng nhập");
            incorrectTimes++;
            return;
        }

        Users u = userRm.findByUserKeyId(userKey);

        if(u.getBlocked()) {
            setResponseMessage("Tài khoản đã bị khóa. Hãy liên hệ với BQT để mở lại tài khoản");
            return;
        }

        HttpSession LoginSs = getRequest().getSession(true);
        LoginSs.setAttribute("userKeyId", userKey);

        String hostURL = getRequest().getServletContext().getAttribute("hostURL").toString();

        if(u.getIsStudent()){
            loginType = "Student";
            setResponseMessage(hostURL + "/Student");
        }
        else if(u.getIsFaculty()){
            loginType = "Faculty";
            setResponseMessage(hostURL + "/Faculty");
        }
        else if(u.getIsAdmin()) {
            loginType = "Admin";
            setResponseMessage(hostURL + "/admin");
        }
        LoginSs.setAttribute("loginType", loginType);
    }

    @TaskToPerform(pathToPerform = "/User/ChangePasswordAction")
    public void ChangePasswordAction(){
        HttpSession LoginSs = getRequest().getSession(false);
        String keyID = String.valueOf(LoginSs.getAttribute("userKeyId"));

        Users updateUser = userRm.findByUserKeyId(keyID);

        String oldPwd = updateUser.getPassword();
        String currentPwd = getParams().get("currentPassword");
        String newPwd = getParams().get("newPassword");
        String confirmPwd = getParams().get("newPasswordAgain");

        if(!currentPwd.equals(oldPwd)){
            setResponseMessage("Mật khẩu hiện tại không đúng");
            return;
        }

        if(!confirmPwd.equals(newPwd)){
            setResponseMessage("Mật khẩu xác không đúng");
            return;
        }

        updateUser.setPassword(newPwd);
        userRm.update(updateUser);
        setResponseMessage("Đổi mật khẩu thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/User/Block")
    public void BlockUser(){
        String id = getParams().get("id");

        if(id == null){
            setResponseMessage("Not Found");
            return;
        }

        userRm.block(id);
        setResponseMessage("Khóa tài khoản thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/User/Unblock")
    public void UnblockUser(){
        String id = getParams().get("id");

        if(id == null){
            setResponseMessage("Not Found");
            return;
        }

        userRm.unblock(id);
        setResponseMessage("Mờ khóa tài khoản thành công");
    }

}
