package controller;

import com.fastlearn.controller.*;
import com.fastlearn.entity.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
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
@WebServlet(name="FacultyServlet", urlPatterns={
    "/Faculty",
    "/Faculty/Query/View",
    "/Faculty/Query/ResponseAction"
})
public class FacultyServlet extends HttpServlet {
   
    @EJB
    private FacultyFacadeRemote facultyRm;
    @EJB
    private MessageFacadeRemote messageRm;
    @EJB
    private QueryFacadeRemote queryRm;
    @EJB
    private QueryDetailsFacadeRemote queryDetailsRm;
    private String pathToPerform;
    private String forwardPage;

    private PrintWriter out = null;

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    private String facultyID = "";
    private String loginType = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pathToPerform = request.getServletPath();
        response.setContentType("text/plain; charset=UTF-8");
        out = response.getWriter();
        this.request = request;
        this.response = response;

        HttpSession LoginSs = request.getSession();

        facultyID = (String)LoginSs.getAttribute("userKeyId");
        loginType = (String)LoginSs.getAttribute("loginType");

        if(facultyID.equals("") && loginType.equals("")) {
            String hostURL = request.getServletContext().getAttribute("hostURL").toString();
            response.sendRedirect(hostURL + "/login.jsp");
        }
    }

    //TYPE : GET
    public void FacultyPage() {
        List<QueryDetails> lstQuery = queryDetailsRm.getStudentNotification(facultyID);

        request.setAttribute("VIEWTYPE", "FULL");
        request.setAttribute("faculty", facultyRm.find(facultyID));
        request.setAttribute("lstMessage", messageRm.forFaculty());
        request.setAttribute("lstQuery", lstQuery);
        request.setAttribute("queryCount", lstQuery.size());
        forwardPage = "Faculty.jsp";
    }

    public void ViewQuery(){
        int id = Integer.parseInt(request.getParameter("id"));

        List<QueryDetails> lstQuery = queryDetailsRm.getFacultyNotification(facultyID);

        request.setAttribute("VIEWTYPE", "QUERY");
        request.setAttribute("faculty", facultyRm.find(facultyID));
        request.setAttribute("query", queryRm.find(id));
        request.setAttribute("lstQuery", lstQuery);
        request.setAttribute("faculty", facultyRm.find(facultyID));
        request.setAttribute("lstMessage", messageRm.forFaculty());
        forwardPage = "../../Faculty.jsp";
    }

    //TYPE : POST
    public void ResponseAction(){
        Integer id = Integer.parseInt(request.getParameter("queryID"));
        String responseText = request.getParameter("responseText");

        if(facultyID != null) {
            QueryDetails responseQuery = queryDetailsRm.find(id);
            responseQuery.setResponseText(responseText);
            responseQuery.setDateOfResponse(Calendar.getInstance().getTime());

            queryDetailsRm.update(responseQuery);

            setMessage("Answer query success");
            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(!loginType.equals("Faculty"))
            return;

        if(pathToPerform.equals("/Faculty")) {
            FacultyPage();
        }
        if(pathToPerform.equals("/Faculty/Query/View")) {
            ViewQuery();
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(!loginType.equals("Faculty"))
            return;

        if(pathToPerform.equals("/Faculty/Query/ResponseAction")) {
            ResponseAction();
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

}
