package controller;

import com.fastlearn.controller.CourseFacadeRemote;
import com.fastlearn.entity.Course;
import java.io.IOException;
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
@WebServlet(name="CourseServlet", urlPatterns={
    "/admin/CourseManage",
    "/admin/Course/Add",
    "/admin/Course/Edit",
    "/admin/Course/Update",
    "/admin/Course/Find",
    "/admin/Course/Info"
})
public class CourseServlet extends HttpServlet {
   
    @EJB
    private CourseFacadeRemote courseRm;
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

        if(pathToPerform.equals("/admin/CourseManage")) {
            request.setAttribute("lstCourse", courseRm.findAll());
            forwardPage = "CourseManage.jsp";
        }
        else if(pathToPerform.equals("/admin/Course/Info")) {
            int id = Integer.parseInt(request.getParameter("id"));

            if(id == 0) {
                setMessage("Wrong URL");
                request.setAttribute("message", message);
                forwardPage = "../../do/error.jsp";
            }
            else {
                Course course = courseRm.find(id);

                request.setAttribute("course", course);
                forwardPage = "../CourseInfo.jsp";
            }
        }        
        else if(pathToPerform.equals("/admin/Course/Edit")) {
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
        else if(pathToPerform.equals("/admin/Course/Find")) {
            String findText = request.getParameter("findtext");
            String findType = request.getParameter("findtype");

            List<Course> lstCourse = null;

            if(findType.equals("name"))
                lstCourse = courseRm.findByName(findText);

            request.setAttribute("lstCourse", lstCourse);
            forwardPage = "../FindCourseResult.jsp";
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        
        if(pathToPerform.equals("/admin/Course/Update")) {
            int courseid = Integer.parseInt(request.getParameter("courseId"));
            String name = request.getParameter("name");
            long fee = Long.parseLong(request.getParameter("fee"));
            String prerequisites = request.getParameter("prerequisites");
            
            if(courseid == 0) {
                setMessage("Not Found");
                request.setAttribute("message", message);
                forwardPage = "../../do/error.jsp";
            } 
            else {
                Course updateCourse = courseRm.find(courseid);
                updateCourse.setName(name);
                updateCourse.setFee(fee);
                updateCourse.setPrerequisites(prerequisites);

                courseRm.update(updateCourse);

                setMessage("Update success");
                request.setAttribute("message", message);
                forwardPage = "../../do/success.jsp";
            }
        }
        else if(pathToPerform.equals("/admin/Course/Add")) {
            String name = request.getParameter("name");
            long fee = Long.parseLong(request.getParameter("fee"));
            String prerequisites = request.getParameter("prerequisites");

            Course newCourse = new Course(name, fee, prerequisites);
            courseRm.insert(newCourse);

            setMessage("Add new course success");
            request.setAttribute("message", message);

            forwardPage = "../../do/success.jsp";
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

}
