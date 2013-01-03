package controller;

import com.fastlearn.controller.*;
import com.fastlearn.entity.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import anotation.*;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.TransactionManagement;
import javax.servlet.http.HttpSession;
import javax.transaction.*;
import model.CustomServlet;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="StudentServlet", urlPatterns={
    "/Student",
    "/Student/Query/View",
    "/Student/Course/View",    
    "/Student/Query/PostAction",
    "/Student/Query/ResponseAction",
    "/Student/Feedback/PostAction",
    "/Student/FindCourseAction",
    "/admin/StudentManage",
    "/admin/Student/Info",
    "/admin/Student/AddAction",
    "/admin/Student/Edit",
    "/admin/Student/UpdateAction",
    "/admin/Student/FindAction",
    "/admin/Student/AssignInfoAction"
})
@TransactionManagement
public class StudentServlet extends CustomServlet {
    @Resource
    private UserTransaction userTrans;

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
    @EJB
    private KeyGenerateFacadeRemote keyRm;
    @EJB
    private UsersFacadeRemote userRm;
    @EJB
    private FacultyFacadeRemote facultyRm;
    @EJB
    private StudyCenterFacadeRemote centerRm;
    @EJB
    private StudentDetailsFacadeRemote studentDetailsRm;

    private String studentID = null;

    @Override
    public void processRequest(){
        HttpSession LoginSs = getRequest().getSession();
        studentID = String.valueOf(LoginSs.getAttribute("userKeyId"));
    }
    
    private ArrayList<Faculty> lstFaculty;
    private ArrayList<List<QueryDetails>> lstNotification;
    private int countNotification;
    
    public void getFaculties(){
        Student student = studentRm.find(studentID);
        
        lstFaculty = new ArrayList<Faculty>();

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
    }

    public void getNotification(){
        lstNotification = new ArrayList<List<QueryDetails>>();

        for(Faculty f : lstFaculty){
            lstNotification.add(queryDetailsRm.getStudentNotification(f.getFacultyID()));
        }

        countNotification = 0;

        for(List<QueryDetails> item : lstNotification){
            countNotification += item.size();
        }
    }

    @TaskToPerform(pathToPerform = "/Student", forwardPage = "Student.jsp")
    public void StudentPage(){
        getFaculties();
        getNotification();

        setAttribute("VIEWTYPE", "FULL");
        setAttribute("lstFaculty", lstFaculty);
        setAttribute("student", studentRm.find(studentID));
        setAttribute("lstMessage", messageRm.forStudent());
        setAttribute("lstNotification", lstNotification);
        setAttribute("notificationCount", countNotification);
    }

    @TaskToPerform(pathToPerform = "/Student/Query/View", forwardPage = "../../Student.jsp")
    public void ViewQuery(){
        int id = Integer.parseInt(getParams().get("id"));

        getFaculties();
        getNotification();

        setAttribute("VIEWTYPE", "QUERY");
        setAttribute("student", studentRm.find(studentID));
        setAttribute("query", queryRm.find(id));
        setAttribute("lstMessage", messageRm.forStudent());
        setAttribute("lstNotification", lstNotification);
        setAttribute("notificationCount", countNotification);
    }

    @TaskToPerform(pathToPerform = "/Student/Course/View", forwardPage = "../../Student.jsp")
    public void ViewCourse() {
        int id = Integer.parseInt(getParams().get("id"));

        getFaculties();
        getNotification();

        setAttribute("VIEWTYPE", "COURSE");
        setAttribute("student", studentRm.find(studentID));
        setAttribute("course", courseRm.find(id));
        setAttribute("lstMessage", messageRm.forStudent());
        setAttribute("lstNotification", lstNotification);
        setAttribute("notificationCount", countNotification);
    }

    @TaskToPerform(pathToPerform = "/Student/Query/PostAction")
    public void PostQueryAction(){
        String facultyID = getParams().get("facultyID");
        String title = getParams().get("title");
        String responseText = getParams().get("responseText");

        Query postQuery = new Query(title, facultyID, studentID);
        QueryDetails queryDetails = new QueryDetails(studentID, responseText);
        queryDetails.setQuery(postQuery);

        queryDetailsRm.insert(queryDetails);

        setResponseMessage("Gửi truy vấn thành công");
    }

    @TaskToPerform(pathToPerform = "/Student/Query/ResponseAction")
    public void ResponseQueryAction(){
        int queryID = Integer.parseInt(getParams().get("queryID"));
        String responseText = getParams().get("responseText");

        Query replyQuery = queryRm.find(queryID);

        QueryDetails replayDetails = new QueryDetails(studentID, responseText);
        replayDetails.setQuery(replyQuery);

        queryDetailsRm.update(replayDetails);

        setResponseMessage("Trả lời truy vấn thành công");
    }

    @TaskToPerform(pathToPerform = "/Student/Feedback/PostAction")
    public void SendFeedbackAction(){
        String feedbackText = getParams().get("feedbackText");

        Feedback newFeedback = new Feedback(studentID, feedbackText);
        fbRm.insert(newFeedback);

        setResponseMessage("Send feedback success");
    }

    @TaskToPerform(pathToPerform = "/Student/FindCourseAction", forwardPage = "../module/Student_FindCourseResult.jsp")
    public void FindCourseAction(){
        String searchText = getParams().get("searchText");

        List<Course> lstCourse = courseRm.findByName(searchText);
        setAttribute("lstCourse", lstCourse);
    }

    @TaskToPerform(pathToPerform = "/admin/StudentManage", forwardPage = "StudentManage.jsp")
    public void StudentManage() {
        setAttribute("lstStudent", studentRm.findAll());
        setAttribute("lstCourse", courseRm.findAll());
        setAttribute("lstFaculty", facultyRm.findAll());
        setAttribute("lstCenter", centerRm.findAll());
    }

    @TaskToPerform(pathToPerform = "/admin/Student/Info", forwardPage = "../StudentInfo.jsp")
    public void StudentInfo() {
        String id = getParams().get("id");

        if(id == null) {
            setResponseMessage("Wrong URL");
            return;
        }
        
        Student student = studentRm.find(id);
        setAttribute("student", student);
    }

    @TaskToPerform(pathToPerform = "/admin/Student/AddAction")
    public void AddStudentAction(){
        boolean raiseException = false;
        String name = getParams().get("name");
        String phone = getParams().get("phone");
        String email = getParams().get("email");
        String address = getParams().get("address");

        if(email.length() == 0)
            email = null;

        try {
            userTrans.begin();

            String codeID = keyRm.insertKey();
            Users newUser = new Users(email, "fastlearn", codeID, true, false, false);
            Student newStudent = new Student(codeID, name, phone, email, address);

            userRm.insert(newUser);
            studentRm.insert(newStudent);

            setResponseMessage("Thêm học viên mới thành công. CodeID: " + codeID);
            userTrans.commit();
        } catch (NotSupportedException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (SystemException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (RollbackException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (SecurityException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (IllegalStateException ex) {
            Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        }
        finally {
            if(raiseException) {
                try {
                    userTrans.rollback();
                    setResponseMessage("Có lỗi trong quá trình thực hiện. Hệ thống đã phục hồi lại trạng thái ban đầu");
                } catch (IllegalStateException ex) {
                    Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SystemException ex) {
                    Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @TaskToPerform(pathToPerform = "/admin/Student/Edit", forwardPage = "../EditStudent.jsp")
    public void EditStudent(){
        String id = getParams().get("id");

        if(id == null) {
            setResponseMessage("Wrong URL");
            return;
        }
        
        Student studentEdit = studentRm.find(id);
        setAttribute("student", studentEdit);
    }

    @TaskToPerform(pathToPerform = "/admin/Student/UpdateAction")
    public void UpdateStudentAction(){
        String studentid = getParams().get("studentID");
        String name = getParams().get("name");
        String phone = getParams().get("phone");
        String email = getParams().get("email");
        String address = getParams().get("address");

        if(studentid == null || studentid.length() < 10) {
            setResponseMessage("Not Found");
            return;
         }
        
        Student updateStudent = studentRm.find(studentid);
        updateStudent.setName(name);
        updateStudent.setPhone(phone);
        updateStudent.setEmail(email);
        updateStudent.setAddress(address);

        studentRm.update(updateStudent);

        setResponseMessage("Cập nhập thông tin học viên thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/Student/FindAction", forwardPage = "../FindStudentResult.jsp")
    public void FindStudentAction(){
        String findText = getParams().get("findText");
        String findType = getParams().get("findType");

        List<Student> lstStudent = null;

        if(findType.equals("name"))
            lstStudent = studentRm.findByName(findText);
        else if(findType.equals("email"))
            lstStudent = studentRm.findByEmail( findText);
        else if(findType.equals("address"))
            lstStudent = studentRm.findByAddress(findText);

        setAttribute("lstStudent", lstStudent);
    }

    @TaskToPerform(pathToPerform = "/admin/Student/AssignInfoAction")
    public void AssignInfoAction(){
        String facultyid = getParams().get("facultyID");
        String studentid = getParams().get("studentID");
        Integer courseid = Integer.parseInt(getParams().get("courseID"));
        Integer centerid = Integer.parseInt(getParams().get("centerID"));

        if(studentDetailsRm.isAssigned(studentid, facultyid, courseid, centerid)){
            setResponseMessage("Assigned");
            return;
        }

        StudentDetails assignStudentInfo = new StudentDetails(studentid, facultyid, courseid, centerid);
        studentDetailsRm.insert(assignStudentInfo);

        setResponseMessage("Assign completed");
    }

}