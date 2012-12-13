package controller;

import com.fastlearn.controller.*;
import com.fastlearn.entity.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private String pathToPerform;
    private String forwardPage;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pathToPerform = request.getServletPath();
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(pathToPerform.equals("/Faculty")) {
            String facultyID = "FL00000002";

            List<Query> lstQuery = queryRm.getFacultyUnresponse(facultyID);

            request.setAttribute("VIEWTYPE", "FULL");
            request.setAttribute("faculty", facultyRm.find(facultyID));
            request.setAttribute("lstMessage", messageRm.forFaculty());
            request.setAttribute("lstQuery", lstQuery);
            request.setAttribute("queryCount", lstQuery.size());
            forwardPage = "Faculty.jsp";
        }
        if(pathToPerform.equals("/Faculty/Query/View")) {
            String facultyID = "FL00000002";
            int id = Integer.parseInt(request.getParameter("id"));

            List<Query> lstQuery = queryRm.getFacultyUnresponse(facultyID);

            request.setAttribute("VIEWTYPE", "QUERY");
            request.setAttribute("faculty", facultyRm.find(facultyID));
            request.setAttribute("query", queryRm.find(id));
            request.setAttribute("faculty", facultyRm.find(facultyID));
            request.setAttribute("lstMessage", messageRm.forFaculty());
            forwardPage = "../../Faculty.jsp";
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(pathToPerform.equals("/Faculty/Query/ResponseAction")) {
            Integer queryID = Integer.parseInt(request.getParameter("queryID"));
            String facultyID = request.getParameter("facultyID");
            String responseText = request.getParameter("responseText");

            if(facultyID == null) {
                setMessage("Please login to perform this task");
                request.setAttribute("message", message);
                forwardPage = "../login.jsp";
            }
            else {
                Query responseQuery = queryRm.find(queryID);
                responseQuery.setResponseText(responseText);
                responseQuery.setDateOfResponse(Calendar.getInstance().getTime());

                queryRm.update(responseQuery);

                setMessage("Answer query success");
                request.setAttribute("message", message);
                forwardPage = "../../do/success.jsp";
            }
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

}
