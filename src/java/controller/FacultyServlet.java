package controller;

import anotation.TaskToPerform;
import com.fastlearn.controller.*;
import com.fastlearn.entity.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.transaction.*;
import model.CustomServlet;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="FacultyServlet", urlPatterns = {
    "/Faculty",
    "/Faculty/Query/View",
    "/Faculty/Query/ResponseAction",
    "/admin/FacultyManage",
    "/admin/Faculty/Info",
    "/admin/Faculty/AddAction",
    "/admin/Faculty/Edit",
    "/admin/Faculty/UpdateAction",
    "/admin/Faculty/CourseAssignAction"
})
public class FacultyServlet extends CustomServlet {

    @Resource
    private UserTransaction userTrans;
   
    @EJB
    private FacultyFacadeRemote facultyRm;
    @EJB
    private MessageFacadeRemote messageRm;
    @EJB
    private QueryFacadeRemote queryRm;
    @EJB
    private QueryDetailsFacadeRemote queryDetailsRm;
    @EJB
    private KeyGenerateFacadeRemote keyRm;
    @EJB
    private UsersFacadeRemote userRm;
    @EJB
    private CourseFacadeRemote courseRm;
    @EJB
    private FacultyDetailsFacadeRemote facultyDetailsRm;

    private String facultyID = "";

    @Override
    public void processRequest(){
        HttpSession LoginSs = getRequest().getSession();
        facultyID = String.valueOf(LoginSs.getAttribute("userKeyId"));
    }

    private ArrayList<Student> lstStudent;
    private ArrayList<List<QueryDetails>> lstNotification;
    private int countNotification;

    public void getStudents(){
        Faculty faculty = facultyRm.find(facultyID);
        lstStudent = new ArrayList<Student>();

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
    }

    public void getNotification(){
        lstNotification = new ArrayList<List<QueryDetails>>();

        for(Student s : lstStudent){
            lstNotification.add(queryDetailsRm.getStudentNotification(s.getStudentID()));
        }

        countNotification = 0;

        for(List<QueryDetails> item : lstNotification){
            countNotification += item.size();
        }
    }

    @TaskToPerform(pathToPerform = "/Faculty", forwardPage = "Faculty.jsp")
    public void FacultyPage() {       

        getStudents();
        getNotification();

        setAttribute("VIEWTYPE", "FULL");
        setAttribute("faculty", facultyRm.find(facultyID));
        setAttribute("lstMessage", messageRm.forFaculty());
        setAttribute("lstNotification", lstNotification);
        setAttribute("notificationCount", countNotification);        
    }

    @TaskToPerform(pathToPerform = "/Faculty/Query/View", forwardPage = "../../Faculty.jsp")
    public void ViewQuery(){
        int id = Integer.parseInt(getParams().get("id"));

        if(getParams().get("markRead") != null){
            int readID = Integer.parseInt(getParams().get("markRead"));
            QueryDetails markReadQuery = queryDetailsRm.find(readID);
            markReadQuery.setIsRead(true);

            queryDetailsRm.update(markReadQuery);
        }

        getStudents();
        getNotification();

        setAttribute("VIEWTYPE", "QUERY");
        setAttribute("faculty", facultyRm.find(facultyID));
        setAttribute("query", queryRm.find(id));
        setAttribute("lstMessage", messageRm.forFaculty());
        setAttribute("lstNotification", lstNotification);
        setAttribute("notificationCount", countNotification);
    }

    @TaskToPerform(pathToPerform = "/Faculty/ResponseAction")
    public void ResponseFacultyAction(){
        Integer id = Integer.parseInt(getParams().get("queryID"));
        String responseText = getParams().get("responseText");

        Query responseQuery = queryRm.find(id);

        QueryDetails responseDetails = new QueryDetails(facultyID, responseText);
        responseDetails.setQuery(responseQuery);

        queryDetailsRm.update(responseDetails);

        setResponseMessage("Trả lời truy vấn thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/FacultyManage", forwardPage = "FacultyManage.jsp")
    public void FacultyManagePage(){
        setAttribute("lstCourse", courseRm.findAll());
        setAttribute("lstFaculty", facultyRm.findAll());
    }

    @TaskToPerform(pathToPerform = "/admin/Faculty/Info", forwardPage = "../FacultyInfo.jsp")
    public void FacultyInfo(){
        String id = getParams().get("id");

        if(id == null) {
            setResponseMessage("Wrong URL");
            return;
        }

        Faculty faculty = facultyRm.find(id);
        setAttribute("faculty", faculty);
    }

    @TaskToPerform(pathToPerform = "/admin/Faculty/AddAction")
    public void AddFacultyAction() throws RollbackException{
        boolean raiseException = false;
        String name = getParams().get("name");
        String address = getParams().get("address");
        String email = getParams().get("email");

        try {
            userTrans.begin();

            String codeID = keyRm.insertKey();
            Users newUser = new Users(email, "fastlearn", codeID, false, true, false);
            Faculty newFaculty = new Faculty(codeID, name, email, address);

            userRm.insert(newUser);
            facultyRm.insert(newFaculty);

            setResponseMessage("Thêm giảng viên mới thành công. CodeID: " + codeID);
            userTrans.commit();
        } catch (NotSupportedException ex) {
            Logger.getLogger(FacultyServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (SystemException ex) {
            Logger.getLogger(FacultyServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (RollbackException ex) {
            Logger.getLogger(FacultyServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(FacultyServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(FacultyServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (SecurityException ex) {
            Logger.getLogger(FacultyServlet.class.getName()).log(Level.SEVERE, null, ex);
            raiseException = true;
        } catch (IllegalStateException ex) {
            Logger.getLogger(FacultyServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    @TaskToPerform(pathToPerform = "/admin/Faculty/Edit", forwardPage = "../EditFaculty.jsp")
    public void EditFaculty(){
        String id = getParams().get("id");

        if(id == null) {
            setResponseMessage("Wrong URL");
            return;
        }

        Faculty facultyEdit = facultyRm.find(id);
        setAttribute("faculty", facultyEdit);
    }

    @TaskToPerform(pathToPerform = "/admin/Faculty/UpdateAction")
    public void UpdateFacultyAction(){
        String facultyid = getParams().get("facultyID");
        String name = getParams().get("name");
        String email = getParams().get("email");
        String address = getParams().get("address");
        
        Faculty updateFaculty = facultyRm.find(facultyid);
        updateFaculty.setName(name);
        updateFaculty.setEmail(email);
        updateFaculty.setAddress(address);

        facultyRm.update(updateFaculty);

        setResponseMessage("Cập nhập thông tin giảng viên thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/Faculty/CourseAssignAction")
    public void CourseAssignAction(){
        String facultyid = getParams().get("facultyID");
        Integer courseid = Integer.parseInt(getParams().get("courseID"));

        if(facultyDetailsRm.isAssigned(facultyid, courseid)){
            setResponseMessage("Assigned");
            return;
        }
        
        FacultyDetails facultyAssign = new FacultyDetails(facultyid, courseid);
        facultyDetailsRm.insert(facultyAssign);

        setResponseMessage("Assign completed");
    }

}