package controller;

import com.fastlearn.controller.UsersFacadeRemote;
import com.fastlearn.entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TUANANH
 */
@WebServlet(name="UserServlet", urlPatterns={"/User/LoginAction", "/User/Logout", "/User/Login", "/User/ChangePasswordAction"})
public class UserServlet extends HttpServlet { 

    @EJB
    private UsersFacadeRemote userRm;

    private PrintWriter out = null;

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    private String pathToPerform;
    private String forwardPage;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        pathToPerform = request.getServletPath();
        response.setContentType("text/html; charset=UTF-8");
        out = response.getWriter();
        this.request = request;
        this.response = response;
    }

    //TYPE : GET
    public void LoginPage(){
        try {
            request.getRequestDispatcher("../login.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LogoutAction(){
        HttpSession loginSs = request.getSession();
        loginSs.invalidate();

        try {
            response.sendRedirect(request.getServletContext().getAttribute("hostURL").toString());
        } catch (IOException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //TYPE : POST
    public void LoginAction(){
        String userKey = request.getParameter("userKeyId");
        String pwd = request.getParameter("password");

        response.setContentType("text/plain; charset=UTF-8");

        if(userRm.userAvailable(userKey, pwd)){
            Users u = userRm.findByUserKeyId(userKey);

            if(u.getBlocked() == 1) {
                out.write("Tài khoản đã bị khóa. Hãy liên hệ với BQT để mở lại tài khoản");
                return;
            }

            HttpSession LoginSs = request.getSession(true);
            LoginSs.setAttribute("userKeyId", userKey);

            String hostURL = request.getServletContext().getAttribute("hostURL").toString();

            if(u.getIsStudent()){
                LoginSs.setAttribute("loginType", "Student");
                out.write(hostURL + "/Student");
            }
            else if(u.getIsFaculty()){
                LoginSs.setAttribute("loginType", "Faculty");
                out.write(hostURL + "/Faculty");
            }
            else if(u.getIsAdmin()) {
                LoginSs.setAttribute("loginType", "Admin");
                out.write(hostURL + "/admin");
            }
        }
        else {
            out.write("Sai thông tin đăng nhập");
        }
    }

    public void ChangePasswordAction(){
        HttpSession LoginSs = request.getSession(false);

        String keyID = String.valueOf(LoginSs.getAttribute("userKeyId"));

        Users updateUser = userRm.find(keyID);

        String oldPwd = updateUser.getPassword();
        String currentPwd = request.getParameter("currentPassword");
        String newPwd = request.getParameter("newPassword");
        String confirmPwd = request.getParameter("newPasswordAgain");

        if(!currentPwd.equals(oldPwd))
            out.write("Mật khẩu hiện tại không đúng");
        else {
            if(!confirmPwd.equals(newPwd))
                out.write("Mật khẩu xác không đúng");
            else {
                updateUser.setPassword(newPwd);
                out.write("Đổi mật khẩu thành công");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        processRequest(request, response);

        if(pathToPerform.equals("/User/Login"))
            LoginPage();
        else if(pathToPerform.equals("/User/Logout"))
            LogoutAction();
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);

        if(pathToPerform.equals("/User/LoginAction"))
            LoginAction();
        if(pathToPerform.equals("/User/ChangePasswordAction"))
            ChangePasswordAction();
    }

}
