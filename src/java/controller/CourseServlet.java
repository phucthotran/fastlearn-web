package controller;

import anotation.TaskToPerform;
import com.fastlearn.controller.CourseFacadeRemote;
import com.fastlearn.entity.Course;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import model.CustomServlet;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="CourseServlet", urlPatterns={
    "/admin/CourseManage",
    "/admin/Course/Info",
    "/admin/Course/AddAction",
    "/admin/Course/Edit",
    "/admin/Course/UpdateAction",
    "/admin/Course/FindAction"
})
public class CourseServlet extends CustomServlet {
   
    @EJB
    private CourseFacadeRemote courseRm;

    @Override
    public void processRequest() {
    }

    @TaskToPerform(pathToPerform = "/admin/CourseManage", forwardPage = "CourseManage.jsp")
    public void CourseManagePage(){
        setAttribute("lstCourse", courseRm.findAll());
    }

    @TaskToPerform(pathToPerform = "/admin/Course/Info", forwardPage = "../CourseInfo.jsp")
    public void CourseInfo(){
        int id = Integer.parseInt(getParams().get("id"));

        if(id == 0) {
            setResponseMessage("Wrong URL");
            return;
        }
        
        setAttribute("course", courseRm.find(id));
    }

    @TaskToPerform(pathToPerform = "/admin/Course/AddAction")
    public void AddCourseAction(){
        String name = getParams().get("name");
        long fee = Long.parseLong(getParams().get("fee"));
        String prerequisites = getParams().get("prerequisites");

        Course newCourse = new Course(name, fee, prerequisites);
        courseRm.insert(newCourse);

        setResponseMessage("Thêm khóa học mới thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/Course/Edit", forwardPage = "../EditCourse.jsp")
    public void EditCourse(){
        int id = Integer.parseInt(getParams().get("id"));

        if(id == 0) {
            setResponseMessage("Wrong URL");
            return;
        }

        Course courseEdit = courseRm.find(id);
        setAttribute("course", courseEdit);
    }

    @TaskToPerform(pathToPerform = "/admin/Course/UpdateAction")
    public void UpdateCourseAction(){
        int courseid = Integer.parseInt(getParams().get("courseID"));
        String name = getParams().get("name");
        long fee = Long.parseLong(getParams().get("fee"));
        String prerequisites = getParams().get("prerequisites");

        if(courseid == 0) {
            setResponseMessage("Not Found");
            return;
        }
        
        Course updateCourse = courseRm.find(courseid);
        updateCourse.setName(name);
        updateCourse.setFee(fee);
        updateCourse.setPrerequisites(prerequisites);

        courseRm.update(updateCourse);

        setResponseMessage("Cập nhập thông tin khóa học thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/Course/FindAction", forwardPage = "../FindCourseResult.jsp")
    public void FindCourseAction(){
        String findText = getParams().get("findText");
        String findType = getParams().get("findType");

        List<Course> lstCourse = null;

        if(findType.equals("name"))
            lstCourse = courseRm.findByName(findText);

        setAttribute("lstCourse", lstCourse);
    }

}
