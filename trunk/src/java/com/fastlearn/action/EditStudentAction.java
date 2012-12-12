package com.fastlearn.action;

import com.fastlearn.controller.StudentFacadeRemote;
import com.fastlearn.entity.Student;
import com.fastlearn.lib.EnterpriseBean;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author ITExplore
 */

public class EditStudentAction extends ActionSupport {

    private String message;
    private String studentID;
    private String name;
    private String email;
    private String phone;
    private String address;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public EditStudentAction() {

    }

    @Override
    public String execute() throws Exception {
        StudentFacadeRemote studentRm = (StudentFacadeRemote)EnterpriseBean.lookupBean("studentRef");

        if(studentID == null || name == null || phone == null || email == null || address == null) {
            setMessage("Please input data");
            return INPUT;
        }

        Student updateStudent = studentRm.find(studentID);
        updateStudent.setName(name);
        updateStudent.setPhone(phone);
        updateStudent.setEmail(email);
        updateStudent.setAddress(address);

        setMessage("Update student success");
        return SUCCESS;
    }

}