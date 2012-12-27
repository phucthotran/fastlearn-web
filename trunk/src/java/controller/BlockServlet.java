package controller;

import com.fastlearn.controller.UsersFacadeRemote;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="BlockServlet", urlPatterns={"/admin/Block", "/admin/Unblock"})
public class BlockServlet extends HttpServlet {

    @EJB
    private UsersFacadeRemote userRm;
    private String pathToPerform;
    private String forwardPage;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    private String keyID = "";
    private String loginType = "";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!response.isCommitted())
            super.service(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pathToPerform = request.getServletPath();

        HttpSession LoginSs = request.getSession();

        keyID = String.valueOf(LoginSs.getAttribute("userKeyId"));
        loginType = String.valueOf(LoginSs.getAttribute("loginType"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        String id = request.getParameter("id");

        if(id == null) {
            setMessage("Not Found");
            request.setAttribute("message", message);
            request.getRequestDispatcher("../do/error.jsp").forward(request, response);
            return;
        }
        else {
            if(pathToPerform.equals("/admin/Block"))
                userRm.block(id);
            else if(pathToPerform.equals("/admin/Unblock"))
                userRm.unblock(id);

            setMessage("Block Success");
            request.setAttribute("message", message);
            forwardPage = "../do/success.jsp";
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

}
