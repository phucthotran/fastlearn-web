package com.fastlearn.action;

import com.fastlearn.controller.QueryFacadeRemote;
import com.fastlearn.entity.Query;
import com.fastlearn.lib.EnterpriseBean;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author ITExplore
 */

public class PostQueryAction extends ActionSupport {

    private String message;
    private String studentID;
    private String facultyID;
    private String queryText;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public PostQueryAction() {

    }

    @Override
    public String execute() throws Exception {
        QueryFacadeRemote queryRm = (QueryFacadeRemote)EnterpriseBean.lookupBean("queryRef");

        if(studentID == null) {
            setMessage("Please login to perform this task");
            return LOGIN;
        }

        if(queryText.length() < 15) {
            setMessage("Query Text must be greater than 15 character");
            return INPUT;
        }

        Query postQuery = new Query(queryText, studentID, facultyID);
        queryRm.insert(postQuery);

        setMessage("Post query success.");
        return SUCCESS;
    }
}