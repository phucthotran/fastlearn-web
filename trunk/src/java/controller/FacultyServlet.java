package controller;

import anotation.MDO;
import com.fastlearn.controller.*;
import com.fastlearn.entity.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import library.ModelDriven;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="FacultyServlet", urlPatterns={
    "/Faculty",
    "/Faculty/Query/View",
    "/Faculty/Query/ResponseAction",
    "/admin/FacultyManage",
    "/admin/Faculty/Info",
    "/admin/Faculty/Edit",
    "/admin/Faculty/UpdateAction"
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

    @MDO
    private static Faculty facl;

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

        HttpSession LoginSs = request.getSession();

        facultyID = String.valueOf(LoginSs.getAttribute("userKeyId"));
        loginType = String.valueOf(LoginSs.getAttribute("loginType"));
    }

    //TYPE : GET
    public void FacultyPage() {
        Faculty faculty = facultyRm.find(facultyID);

        ArrayList<Student> lstStudent = new ArrayList<Student>();

        //Get Faculty Lít (Not Duplication)
        for(StudentDetails details : faculty.getStudentDetails()) {
            boolean duplication = false;
            String studentName = details.getStudent().getName();

            for(Student std : lstStudent) {
                if(std.getName().equals(studentName)) {
                    duplication = true;
                    break;
                }
            }

            if(!duplication)
                lstStudent.add(details.getStudent());
        }

        ArrayList<List<QueryDetails>> lstNotification = new ArrayList<List<QueryDetails>>();

        for(Student s : lstStudent){
            lstNotification.add(queryDetailsRm.getStudentNotification(s.getStudentID()));
        }

        int countNotification = 0;

        for(List<QueryDetails> item : lstNotification){
            countNotification += item.size();
        }

        request.setAttribute("VIEWTYPE", "FULL");
        request.setAttribute("faculty", facultyRm.find(facultyID));
        request.setAttribute("lstMessage", messageRm.forFaculty());
        request.setAttribute("lstNotification", lstNotification);
        request.setAttribute("notificationCount", countNotification);
        forwardPage = "Faculty.jsp";
    }

    public void ViewQuery(){
        int id = Integer.parseInt(request.getParameter("id"));

        if(request.getParameter("markRead") != null){
            int readID = Integer.parseInt(request.getParameter("markRead"));
            QueryDetails markReadQuery = queryDetailsRm.find(readID);
            markReadQuery.setIsRead(true);

            queryDetailsRm.update(markReadQuery);
        }

        Faculty faculty = facultyRm.find(facultyID);

        ArrayList<Student> lstStudent = new ArrayList<Student>();

        //Get Faculty Lít (Not Duplication)
        for(StudentDetails details : faculty.getStudentDetails()) {
            boolean duplication = false;
            String studentName = details.getStudent().getName();

            for(Student std : lstStudent) {
                if(std.getName().equals(studentName)) {
                    duplication = true;
                    break;
                }
            }

            if(!duplication)
                lstStudent.add(details.getStudent());
        }

        ArrayList<List<QueryDetails>> lstNotification = new ArrayList<List<QueryDetails>>();

        for(Student s : lstStudent){
            lstNotification.add(queryDetailsRm.getStudentNotification(s.getStudentID()));
        }

        int countNotification = 0;

        for(List<QueryDetails> item : lstNotification){
            countNotification += item.size();
        }

        request.setAttribute("VIEWTYPE", "QUERY");
        request.setAttribute("faculty", faculty);
        request.setAttribute("query", queryRm.find(id));
        request.setAttribute("lstNotification", lstNotification);
        request.setAttribute("notificationCount", countNotification);
        request.setAttribute("lstMessage", messageRm.forFaculty());
        forwardPage = "../../Faculty.jsp";
    }

    public void FacultyManagePage(){
        request.setAttribute("lstFaculty", facultyRm.findAll());
        forwardPage = "FacultyManage.jsp";
    }

    public void FacultyInfo(){
        String id = request.getParameter("id");

        if(id == null) {
            setMessage("Wrong URL");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Faculty faculty = facultyRm.find(id);
            request.setAttribute("faculty", faculty);
            forwardPage = "../FacultyInfo.jsp";
        }
    }

    public void EditFaculty(){
        String id = request.getParameter("id");

        if(id == null) {
            setMessage("Wrong URL");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Faculty facultyEdit = facultyRm.find(id);
            request.setAttribute("faculty", facultyEdit);
            forwardPage = "..//EditFaculty.jsp";
        }
    }

    //TYPE : POST
    public void ResponseAction(){
        Integer id = Integer.parseInt(request.getParameter("queryID"));
        String responseText = request.getParameter("responseText");

        if(facultyID != null) {
            Query responseQuery = queryRm.find(id);

            QueryDetails responseDetails = new QueryDetails(facultyID, responseText);
            responseDetails.setQuery(responseQuery);

            queryDetailsRm.update(responseDetails);

            setMessage("Answer query success");
            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
    }

    public void UpdateFacultyAction(){
        if(facl.getFacultyID() == null) {
            setMessage("Not Found");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Faculty updateFaculty = facultyRm.find(facl.getFacultyID());
            updateFaculty.setName(facl.getName());
            updateFaculty.setAddress(facl.getAddress());

            facultyRm.update(updateFaculty);

            setMessage("Update success");
            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(pathToPerform.equals("/Faculty")) {
            FacultyPage();
        }
        else if(pathToPerform.equals("/Faculty/Query/View")) {
            ViewQuery();
        }
        else if(pathToPerform.equals("/admin/FacultyManage")) {
            FacultyManagePage();
        }
        else if(pathToPerform.equals("/admin/Faculty/Info")) {
            FacultyInfo();
        }
        else if(pathToPerform.equals("/admin/Faculty/Edit")) {
            EditFaculty();
        }

        if(forwardPage != null)
            request.getRequestDispatcher(forwardPage).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        ModelDriven.setRequest(request);

        try {
            ModelDriven.parser(StudentServlet.class);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(pathToPerform.equals("/Faculty/Query/ResponseAction")) {
            ResponseAction();
        }
        else if(pathToPerform.equals("/admin/Faculty/UpdateAction")) {
            UpdateFacultyAction();
        }

        if(forwardPage != null)
            request.getRequestDispatcher(forwardPage).forward(request, response);
    }

}
