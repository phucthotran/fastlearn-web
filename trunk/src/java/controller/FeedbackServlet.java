package controller;

import com.fastlearn.controller.FeedbackFacadeRemote;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name="FeedbackServlet", urlPatterns={"/admin/FeedbackManage"})
public class FeedbackServlet extends HttpServlet {

    @EJB
    private FeedbackFacadeRemote fbRm;

    private String pathToPerform;
    private String forwardPage;

    private PrintWriter out = null;

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!response.isCommitted())
            super.service(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pathToPerform = request.getServletPath();
        response.setContentType("text/plain; charset=UTF-8");
        out = response.getWriter();
        this.request = request;
        this.response = response;
    }

    //TYPE : GET
    public void FeedbackManagePage(){
        request.setAttribute("lstFeedback", fbRm.findAll());
        forwardPage = "FeedbackManage.jsp";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(pathToPerform.equals("/admin/FeedbackManage"))
            FeedbackManagePage();

        if(forwardPage != null)
            request.getRequestDispatcher(forwardPage).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
