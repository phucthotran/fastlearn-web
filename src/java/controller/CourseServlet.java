package controller;

import anotation.MDO;
import com.fastlearn.controller.CourseFacadeRemote;
import com.fastlearn.entity.Course;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name="CourseServlet", urlPatterns={
    "/admin/CourseManage",
    "/admin/Course/AddAction",
    "/admin/Course/Edit",
    "/admin/Course/UpdateAction",
    "/admin/Course/Find",
    "/admin/Course/Info"
})
public class CourseServlet extends HttpServlet {
   
    @EJB
    private CourseFacadeRemote courseRm;
    
    @MDO
    private static Course course;

    private String pathToPerform;
    private String forwardPage;

    private PrintWriter out = null;

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    private String keyID = "";
    private String loginType = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pathToPerform = request.getServletPath();
        response.setContentType("text/plain; charset=UTF-8");
        out = response.getWriter();
        this.request = request;
        this.response = response;

        HttpSession LoginSs = request.getSession();

        keyID = (String)LoginSs.getAttribute("userKeyId");
        loginType = (String)LoginSs.getAttribute("loginType");

        if(keyID.equals("") && loginType.equals("")) {
            String hostURL = request.getServletContext().getAttribute("hostURL").toString();
            response.sendRedirect(hostURL + "/login.jsp");
        }
    }

    public void CourseManagePage(){
        request.setAttribute("lstCourse", courseRm.findAll());
        forwardPage = "CourseManage.jsp";
    }

    public void CourseInfo(){
        int id = Integer.parseInt(request.getParameter("id"));

        if(id == 0) {
            setMessage("Wrong URL");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Course c = courseRm.find(id);

            request.setAttribute("course", c);
            forwardPage = "../CourseInfo.jsp";
        }
    }

    public void EditCourse(){
        int id = Integer.parseInt(request.getParameter("id"));

        if(id == 0) {
            setMessage("Wrong URL");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Course courseEdit = courseRm.find(id);
            request.setAttribute("course", courseEdit);
            forwardPage = "../EditCourse.jsp";
        }
    }

    public void FindCourse(){
        String findText = request.getParameter("findtext");
        String findType = request.getParameter("findtype");

        List<Course> lstCourse = null;

        if(findType.equals("name"))
            lstCourse = courseRm.findByName(findText);

        request.setAttribute("lstCourse", lstCourse);
        forwardPage = "../FindCourseResult.jsp";
    }

    //TYPE : POST
    public void UpdateCourseAction(){
        //int courseid = Integer.parseInt(request.getParameter("courseId"));
        //String name = request.getParameter("name");
        //long fee = Long.parseLong(request.getParameter("fee"));
        //String prerequisites = request.getParameter("prerequisites");

        if(course.getCourseID() == 0) {
            setMessage("Not Found");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Course updateCourse = courseRm.find(course.getCourseID());
            updateCourse.setName(course.getName());
            updateCourse.setFee(course.getFee());
            updateCourse.setPrerequisites(course.getPrerequisites());

            courseRm.update(updateCourse);

            setMessage("Update success");
            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
    }

    public void AddCourseAction(){
        //String name = request.getParameter("name");
        //long fee = Long.parseLong(request.getParameter("fee"));
        //String prerequisites = request.getParameter("prerequisites");

        //Course newCourse = new Course(name, fee, prerequisites);
        //courseRm.insert(newCourse);

        courseRm.insert(course);

        setMessage("Add new course success");
        request.setAttribute("message", message);

        forwardPage = "../../do/success.jsp";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(!loginType.equals("Admin"))
            return;

        if(pathToPerform.equals("/admin/CourseManage")) {
            CourseManagePage();
        }
        else if(pathToPerform.equals("/admin/Course/Info")) {
            CourseInfo();
        }        
        else if(pathToPerform.equals("/admin/Course/Edit")) {
            EditCourse();
        }
        else if(pathToPerform.equals("/admin/Course/Find")) {
            FindCourse();
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(!loginType.equals("Admin"))
            return;

        ModelDriven.setRequest(request);

        try {
            ModelDriven.parser(CourseServlet.class);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CourseServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CourseServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CourseServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(pathToPerform.equals("/admin/Course/UpdateAction")) {
            UpdateCourseAction();
        }
        else if(pathToPerform.equals("/admin/Course/AddAction") && loginType.equals("Admin")) {
            AddCourseAction();
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

}
