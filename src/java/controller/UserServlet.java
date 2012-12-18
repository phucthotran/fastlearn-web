package controller;

import com.fastlearn.controller.UsersFacadeRemote;
import com.fastlearn.entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TUANANH
 */
@WebServlet(name="UserServlet", urlPatterns={"/User/LoginAction"})
public class UserServlet extends HttpServlet { 

    @EJB
    private UsersFacadeRemote userRm;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);

        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String userKey = request.getParameter("userKeyId");
        String pwd = request.getParameter("password");

        if(userRm.userAvailable(userKey, pwd)){
            Users u = userRm.findByUserKeyId(userKey);
            
            if(u.getBlocked() == 1) {
                out.write("Tài khoản đã bị khóa. Hãy liên hệ với BQT để mở lại tài khoản");
                return;
            }

            HttpSession LoginSs = request.getSession(true);
            LoginSs.setAttribute("userKeyId", userKey);

            //Cookie LoginCk = new Cookie("userKeyId", userKey);
            //Cookie LoginTypeCk = null;

            String hostURL = request.getServletContext().getAttribute("hostURL").toString();

            if(u.getIsStudent()){
                //LoginTypeCk = new Cookie("LoginType", "Student");
                LoginSs.setAttribute("LoginType", "Student");
                out.write(hostURL + "/Student");
            }
            else if(u.getIsFaculty()){
                //LoginTypeCk = new Cookie("LoginType", "Faculty");
                LoginSs.setAttribute("LoginType", "Faculty");
                out.write(hostURL + "/Faculty");
            }
            else if(u.getIsAdmin()) {
                //LoginTypeCk = new Cookie("LoginType", "Admin");
                LoginSs.setAttribute("LoginType", "Admin");
                out.write(hostURL + "/admin");
            }

            //LoginCk.setMaxAge(60 * 30);
            //LoginTypeCk.setMaxAge(60 * 30);

            //response.addCookie(LoginCk);
            //response.addCookie(LoginTypeCk);
        }
        else {
            out.write("Sai thông tin đăng nhập");
        }
    }

}
