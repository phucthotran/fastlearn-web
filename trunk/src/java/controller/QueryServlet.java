package controller;

import com.fastlearn.controller.*;
import com.fastlearn.entity.*;
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
@WebServlet(name="QueryServlet", urlPatterns={"/Query/RemoveAction"})
public class QueryServlet extends HttpServlet {
   
    @EJB
    private QueryFacadeRemote queryRm;

    private String keyID = "";
    private String loginType = "";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!response.isCommitted())
            super.service(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession LoginSs = request.getSession();

        keyID = String.valueOf(LoginSs.getAttribute("userKeyId"));
        loginType = String.valueOf(LoginSs.getAttribute("loginType"));
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);

        int id = Integer.parseInt(request.getParameter("id"));

        Query qr = queryRm.find(id);
        queryRm.remove(qr);

        request.setAttribute("message", "Remove query success");
        request.getRequestDispatcher("../do/success.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
