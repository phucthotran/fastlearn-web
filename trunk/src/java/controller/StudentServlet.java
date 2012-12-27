package controller;

import com.fastlearn.controller.*;
import com.fastlearn.entity.*;
import java.io.*;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import anotation.*;
import java.util.ArrayList;
import java.util.logging.*;
import javax.servlet.http.HttpSession;
import library.ModelDriven;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="StudentServlet", urlPatterns={
    "/Student",
    "/Student/FindCourseAction",
    "/Student/Course/View",
    "/Student/Query/View",
    "/Student/Query/PostAction",
    "/Student/Query/ReplyAction",
    "/Student/SendFeedbackAction",
    "/admin/StudentManage",
    "/admin/Student/Edit",
    "/admin/Student/UpdateAction",
    "/admin/Student/FindAction",
    "/admin/Student/Info"
})
public class StudentServlet extends HttpServlet {

    @EJB
    private StudentFacadeRemote studentRm;
    @EJB
    private CourseFacadeRemote courseRm;
    @EJB
    private MessageFacadeRemote messageRm;
    @EJB
    private QueryFacadeRemote queryRm;
    @EJB
    private QueryDetailsFacadeRemote queryDetailsRm;
    @EJB
    private FeedbackFacadeRemote fbRm;

    @MDO
    private static Student std;
    @MDO
    private static Query qr;
    @MDO
    private static QueryDetails qrDetails;

    private PrintWriter out = null;

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    private String pathToPerform;
    private String forwardPage;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    private String studentID = null;
    private String loginType = null;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!response.isCommitted())
            super.service(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pathToPerform = request.getServletPath();
        response.setContentType("text/html; charset=UTF-8");
        out = response.getWriter();
        this.request = request;
        this.response = response;

        HttpSession LoginSs = request.getSession();

        studentID = String.valueOf(LoginSs.getAttribute("userKeyId"));
        loginType = String.valueOf(LoginSs.getAttribute("loginType"));
    }

    //TYPE : GET
    public void StudentPage(){
        Student student = studentRm.find(studentID);
        ArrayList<Faculty> lstFaculty = new ArrayList<Faculty>();

        //Get Faculty Lít (Not Duplication)
        for(StudentDetails details : student.getStudentDetails()) {
            boolean duplication = false;
            String facultyName = details.getFaculty().getName();

            for(Faculty faculty : lstFaculty) {
                if(faculty.getName().equals(facultyName)) {
                    duplication = true;
                    break;
                }
            }

            if(!duplication)
                lstFaculty.add(details.getFaculty());
        }

        ArrayList<List<QueryDetails>> lstNotification = new ArrayList<List<QueryDetails>>();

        for(Faculty f : lstFaculty){
            lstNotification.add(queryDetailsRm.getStudentNotification(f.getFacultyID()));
        }

        int countNotification = 0;

        for(List<QueryDetails> item : lstNotification){
            countNotification += item.size();
        }

        request.setAttribute("VIEWTYPE", "FULL");
        request.setAttribute("lstFaculty", lstFaculty);
        request.setAttribute("student", student);
        request.setAttribute("lstMessage", messageRm.forStudent());
        request.setAttribute("lstNotification", lstNotification);
        request.setAttribute("notificationCount", countNotification);
        forwardPage = "Student.jsp";
    }

    public void ViewQuery(){
        int id = Integer.parseInt(request.getParameter("id"));

        List<QueryDetails> lstQuery = queryDetailsRm.getStudentNotification(studentID);

        request.setAttribute("VIEWTYPE", "QUERY");
        request.setAttribute("student", studentRm.find(studentID));
        request.setAttribute("query", queryRm.find(id));
        request.setAttribute("lstQuery", lstQuery);
        request.setAttribute("queryCount", lstQuery.size());
        forwardPage = "../../Student.jsp";
    }

    public void ViewCourse() {
        int id = Integer.parseInt(request.getParameter("id"));

        List<QueryDetails> lstQuery = queryDetailsRm.getStudentNotification(studentID);

        request.setAttribute("VIEWTYPE", "COURSE");
        request.setAttribute("student", studentRm.find(studentID));
        request.setAttribute("course", courseRm.find(id));
        request.setAttribute("lstQuery", lstQuery);
        request.setAttribute("queryCount", lstQuery.size());
        forwardPage = "../../Student.jsp";
    }

    public void StudentManage() {
        request.setAttribute("lstStudent", studentRm.findAll());
        forwardPage = "StudentManage.jsp";
    }

    public void StudentInfo() {
        String id = request.getParameter("id");

        if(id == null) {
            setMessage("Wrong URL");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Student student = studentRm.find(id);
            request.setAttribute("student", student);
            forwardPage = "../StudentInfo.jsp";
        }
    }

    public void EditStudent(){
        String id = request.getParameter("id");

        if(id == null) {
            setMessage("Wrong URL");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Student studentEdit = studentRm.find(id);
            request.setAttribute("student", studentEdit);
            forwardPage = "..//EditStudent.jsp";
        }
    }

    public void FindStudentAction(){
        String findText = request.getParameter("findText");
        String findType = request.getParameter("findType");

        List<Student> lstStudent = null;

        if(findType.equals("name"))
            lstStudent = studentRm.findByName(findText);
        else if(findType.equals("email"))
            lstStudent = studentRm.findByEmail( findText);
        else if(findType.equals("address"))
            lstStudent = studentRm.findByAddress(findText);

        request.setAttribute("lstStudent", lstStudent);

        forwardPage = "../FindStudentResult.jsp";
    }

    //TYPE : POST
    public void FindCourseAction(){
        String searchText = request.getParameter("searchtext");

        if(searchText == null)
            searchText = "";

        List<Course> lstCourse = courseRm.findByName(searchText);
        request.setAttribute("lstCourse", lstCourse);
        forwardPage = "../module/Student_FindCourseResult.jsp";
    }

    public void SendFeedbackAction(){
        String feedbackText = request.getParameter("feedbackText");

        if(studentID != null) {
            Feedback newFeedback = new Feedback(studentID, feedbackText);
            fbRm.insert(newFeedback);

            setMessage("Send feedback success");
            request.setAttribute("message", message);
            forwardPage = "../do/success.jsp";
        }
    }

    public void PostQueryAction(){
        //String facultyID = request.getParameter("facultyID");
        //String title = request.getParameter("title");
        //String queryText = request.getParameter("queryText");

        if(studentID == null) {
            setMessage("Please login to perform this task");
            request.setAttribute("message", message);
            forwardPage = "../login.jsp";
        }
        else {
            //Query postQuery = new Query(title);
            //QueryDetails queryDetails = new QueryDetails(studentID, facultyID, queryText);
            //queryDetails.setQuery(postQuery);

            //queryDetailsRm.insert(queryDetails);

            qr.setStudentID(studentID);

            qrDetails.setQuery(qr);
            qrDetails.setOwnerID(studentID);

            queryDetailsRm.insert(qrDetails);

            setMessage("Post query success");
            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
    }

    public void ReplyQueryAction(){
        if(studentID == null) {
            setMessage("Please login to perform this task");
            request.setAttribute("message", message);
            forwardPage = "../login.jsp";
        }
        else {
            int queryID = Integer.parseInt(request.getParameter("queryID"));
            String responseText = request.getParameter("responseText");
            //String facultyID = request.getParameter("facultyID");

            Query replyQuery = queryRm.find(queryID);

            QueryDetails replayDetails = new QueryDetails(studentID, responseText);
            replayDetails.setQuery(replyQuery);

            queryDetailsRm.update(replayDetails);

            setMessage("Reply query success");
            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
    }

    public void UpdateStudentAction(){
        if(std.getStudentID() == null) {
            setMessage("Not Found");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Student updateStudent = studentRm.find(std.getStudentID());
            updateStudent.setName(std.getName());
            updateStudent.setPhone(std.getPhone());
            updateStudent.setEmail(std.getEmail());
            updateStudent.setAddress(std.getAddress());

            studentRm.update(updateStudent);

            setMessage("Update success");
            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        
        if(pathToPerform.equals("/Student")) {
            StudentPage();
        }
        else if(pathToPerform.equals("/Student/Query/View")) {
            ViewQuery();
        }
        else if(pathToPerform.equals("/Student/Course/View")) {
            ViewCourse();
        }
        else if(pathToPerform.equals("/admin/StudentManage")) {
            StudentManage();
        }
        else if(pathToPerform.equals("/admin/Student/Info")) {
            StudentInfo();
        }
        else if(pathToPerform.equals("/admin/Student/Edit")) {
            EditStudent();
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
        
        if(pathToPerform.equals("/Student/FindCourseAction")) {
            FindCourseAction();
        }
        else if(pathToPerform.equals("/Student/SendFeedbackAction"))
        {
            SendFeedbackAction();
        }
        else if(pathToPerform.equals("/Student/Query/PostAction")) {
            PostQueryAction();
        }
        else if(pathToPerform.equals("/Student/Query/ReplyAction")) {
            ReplyQueryAction();
        }
        else if(pathToPerform.equals("/admin/Student/UpdateAction")) {
            UpdateStudentAction();
        }
        else if(pathToPerform.equals("/admin/Student/FindAction")) {
            FindStudentAction();
        }

        if(forwardPage != null)
            request.getRequestDispatcher(forwardPage).forward(request, response);
    }

}