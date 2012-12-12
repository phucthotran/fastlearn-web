package com.fastlearn.action;

import com.fastlearn.controller.CourseFacadeRemote;
import com.fastlearn.entity.Course;
import com.fastlearn.lib.EnterpriseBean;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author ITExplore
 */

public class AddCourseAction extends ActionSupport {

    private String message;
    private String name;
    private long fee;
    private String prerequisites;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public AddCourseAction() {

    }

    @Override
    public String execute() throws Exception {
        CourseFacadeRemote courseRm = (CourseFacadeRemote)EnterpriseBean.lookupBean("courseRef");

        if(name.length() <= 1 || String.valueOf(fee).length() <= 1 || prerequisites.length() <= 1) {
            setMessage("Please input course information");
            return INPUT;
        }

        Course newCourse = new Course(name, fee, prerequisites);
        courseRm.insert(newCourse);

        setMessage("Add new course success");
        return SUCCESS;
    }

}