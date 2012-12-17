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

        PrintWriter out = response.getWriter();

        String userKey = request.getParameter("userKeyId");
        String pwd = request.getParameter("password");

        if(userRm.userAvailable(userKey, pwd)){
            Cookie saveCookie=new Cookie("userKeyId", userKey);
            response.addCookie(saveCookie);

            Users u = userRm.find(userKey);
            String hostURL = request.getServletContext().getAttribute("hostURL").toString();

            if(u.getIsStudent()){
                response.sendRedirect(hostURL + "/Student");
            }
            else if(u.getIsFaculty()){
                response.sendRedirect(hostURL + "/Faculty");
            }
        }
    }

}
