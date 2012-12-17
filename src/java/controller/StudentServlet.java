package controller;

import com.fastlearn.controller.*;
import com.fastlearn.entity.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.ModelDriven;
import anotation.*;
import java.util.Vector;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="StudentServlet", urlPatterns={
    "/Student",
    "/Student/FindCourse",
    "/Student/Course/View",
    "/Student/Query/View",
    "/Student/Query/Unresponse",
    "/Student/Query/PostAction",
    "/Student/SendFeedback",
    "/admin/StudentManage",
    "/admin/Student/Edit",
    "/admin/Student/Update",
    "/admin/Student/Find",
    "/admin/Student/Info"
})
@MDC
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

    @PropertyNames(name = "std")
    private String[] properyNames;
    @PropertyValues(name = "std")
    private String[] propertyValues;
    @MDO(name = "std")
    private Student std;

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

        if(pathToPerform.equals("/Student")) {
            String studentID = "FL00000001";

            Student student = studentRm.find(studentID);
            Vector<Faculty> lstFaculty = new Vector<Faculty>();

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

            List<QueryDetails> lstQuery = queryDetailsRm.getStudentNotification(studentID);

            request.setAttribute("VIEWTYPE", "FULL");
            request.setAttribute("lstFaculty", lstFaculty);
            request.setAttribute("student", student);
            request.setAttribute("lstMessage", messageRm.forStudent());
            request.setAttribute("lstQuery", lstQuery);
            request.setAttribute("queryCount", lstQuery.size());
            forwardPage = "Student.jsp";
        }
        else if(pathToPerform.equals("/Student/Query/View")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String studentID = "FL00000001";

            List<QueryDetails> lstQuery = queryDetailsRm.getStudentNotification(studentID);

            request.setAttribute("VIEWTYPE", "QUERY");
            request.setAttribute("student", studentRm.find(studentID));
            request.setAttribute("query", queryRm.find(id));
            request.setAttribute("lstQuery", lstQuery);
            request.setAttribute("queryCount", lstQuery.size());
            forwardPage = "../../Student.jsp";
        }        
        else if(pathToPerform.equals("/Student/Course/View")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String studentID = "FL00000001";

            List<QueryDetails> lstQuery = queryDetailsRm.getStudentNotification(studentID);

            request.setAttribute("VIEWTYPE", "COURSE");
            request.setAttribute("student", studentRm.find(studentID));
            request.setAttribute("course", courseRm.find(id));
            request.setAttribute("lstQuery", lstQuery);
            request.setAttribute("queryCount", lstQuery.size());
            forwardPage = "../../Student.jsp";
        }
        else if(pathToPerform.equals("/admin/StudentManage")) {
            request.setAttribute("lstStudent", studentRm.findAll());
            forwardPage = "StudentManage.jsp";
        }
        else if(pathToPerform.equals("/admin/Student/Info")) {
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
        else if(pathToPerform.equals("/admin/Student/Edit")) {
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
        else if(pathToPerform.equals("/admin/Student/Find")) {
            String findText = request.getParameter("findtext");
            String findType = request.getParameter("findtype");

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

        request.getRequestDispatcher(forwardPage).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        ModelDriven.setRequest(request);
        properyNames = ModelDriven.getPropertyNames();
        propertyValues = ModelDriven.getPropertyValues();

        try {
            ModelDriven.parser(StudentServlet.class);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(pathToPerform.equals("/Student/FindCourse")) {
            String searchText = request.getParameter("searchtext");

            List<Course> lstCourse = courseRm.findByName(searchText);
            request.setAttribute("lstCourse", lstCourse);
            forwardPage = "../module/Student_FindCourse.jsp";
        }
        else if(pathToPerform.equals("/Student/SendFeedback"))
        {
            String studentID = request.getParameter("studentID");
            String feedbackText = request.getParameter("feedbackText");

            if(studentID == null) {
                setMessage("Please login to perform this task");
                request.setAttribute("message", message);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            else {
                Feedback newFeedback = new Feedback(studentID, feedbackText);
                fbRm.insert(newFeedback);

                setMessage("Send feedback success");
                request.setAttribute("message", message);
                forwardPage = "../do/success.jsp";
            }
        }
        else if(pathToPerform.equals("/Student/Query/PostAction")) {
            String studentID = request.getParameter("studentID");
            String facultyID = request.getParameter("facultyID");
            String title = request.getParameter("title");
            String queryText = request.getParameter("queryText");

            if(studentID == null) {
                setMessage("Please login to perform this task");
                request.setAttribute("message", message);
                forwardPage = "../login.jsp";
            }
            else {
                Query postQuery = new Query(title);
                QueryDetails queryDetails = new QueryDetails(studentID, facultyID, queryText);
                queryDetails.setQuery(postQuery);

                queryDetailsRm.insert(queryDetails);

                setMessage("Post query success");
                request.setAttribute("message", message);
                forwardPage = "../../do/success.jsp";                
            }
        }
        else if(pathToPerform.equals("/admin/Student/Update")) {            
            if(std.getStudentID() == null) {
                setMessage("Not Found");
                request.setAttribute("message", message);
                forwardPage = "../../do/error.jsp";
            }
            else {
                Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, std.getStudentID());
                Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, std.getName());
                Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, std.getEmail());
                Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, std.getAddress());
                Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, std.getPhone());
                /*
                Student updateStudent = studentRm.find(std.getStudentID());
                updateStudent.setName(std.getName());
                updateStudent.setPhone(std.getPhone());
                updateStudent.setEmail(std.getEmail());
                updateStudent.setAddress(std.getAddress());

                studentRm.update(updateStudent);
                */
                setMessage("Update success");
                request.setAttribute("message", message);
                forwardPage = "../../do/success.jsp";
            }
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

}
