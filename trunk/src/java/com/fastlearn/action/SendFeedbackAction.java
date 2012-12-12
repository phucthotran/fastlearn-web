package com.fastlearn.action;

import com.fastlearn.controller.FeedbackFacadeRemote;
import com.fastlearn.entity.Feedback;
import com.fastlearn.lib.EnterpriseBean;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author ITExplore
 */

public class SendFeedbackAction extends ActionSupport {

    private String message;
    private String studentID;
    private String feedbackText;

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

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public SendFeedbackAction() {

    }

    @Override
    public String execute() throws Exception {
        FeedbackFacadeRemote feedbackRm = (FeedbackFacadeRemote)EnterpriseBean.lookupBean("feedbackRef");

        if(feedbackText == null || feedbackText.length() < 15) {
            setMessage("Feedback Text must be greater than 15 character");
            return INPUT;
        }

        Feedback newFeedback = new Feedback(studentID, feedbackText);
        feedbackRm.insert(newFeedback);

        setMessage("Send feedback success");
        return SUCCESS;
    }

}