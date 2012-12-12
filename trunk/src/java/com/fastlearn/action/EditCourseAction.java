package com.fastlearn.action;

import com.fastlearn.controller.CourseFacadeRemote;
import com.fastlearn.entity.Course;
import com.fastlearn.lib.EnterpriseBean;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author ITExplore
 */

public class EditCourseAction extends ActionSupport {

    private String message;
    private int courseid;
    private String name;
    private long fee;
    private String prerequisites;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCourseId() {
        return courseid;
    }

    public void setCourseId(int courseid) {
        this.courseid = courseid;
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

    public EditCourseAction() {

    }

    @Override
    public String execute() throws Exception {
        CourseFacadeRemote courseRm = (CourseFacadeRemote)EnterpriseBean.lookupBean("courseRef");

        if(courseid == 0) {
            setMessage("Not Found");
            return ERROR;
        }

        if(name.length() <= 1 || String.valueOf(fee).length() <= 1 || prerequisites.length() <= 1) {
            setMessage("Please input course information");
            return INPUT;
        }

        Course updateCourse = courseRm.find(courseid);
        updateCourse.setName(name);
        updateCourse.setFee(fee);
        updateCourse.setPrerequisites(prerequisites);

        courseRm.update(updateCourse);

        setMessage("Update success");
        return SUCCESS;
    }
}