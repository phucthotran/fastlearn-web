package com.fastlearn.action;

import com.fastlearn.controller.QueryFacadeRemote;
import com.fastlearn.entity.Query;
import com.fastlearn.lib.EnterpriseBean;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Calendar;

/**
 *
 * @author ITExplore
 */

public class AnswerQueryAction extends ActionSupport {

    private String message;
    private Integer queryID;
    private String facultyID;
    private String responseText;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getQueryID() {
        return queryID;
    }

    public void setQueryID(Integer queryID) {
        this.queryID = queryID;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public AnswerQueryAction() {

    }

    @Override
    public String execute() throws Exception {
        QueryFacadeRemote queryRm = (QueryFacadeRemote)EnterpriseBean.lookupBean("queryRef");

        if(facultyID == null) {
            setMessage("Please login to perform this task");
            return LOGIN;
        }

        if(responseText.length() < 15) {
            setMessage("Response Text must be greater than 15 character");
            return INPUT;
        }

        Query responseQuery = queryRm.find(queryID);
        responseQuery.setResponseText(responseText);
        responseQuery.setDateOfResponse(Calendar.getInstance().getTime());

        queryRm.update(responseQuery);

        setMessage("Answer query success");
        return SUCCESS;
    }
}