package controller;

import anotation.TaskToPerform;
import com.fastlearn.controller.FeedbackFacadeRemote;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import model.CustomServlet;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="FeedbackServlet", urlPatterns={"/admin/FeedbackManage"})
public class FeedbackServlet extends CustomServlet {

    @EJB
    private FeedbackFacadeRemote fbRm;

    @Override
    public void processRequest() {
    }

    @TaskToPerform(pathToPerform = "/admin/FeedbackManage", forwardPage = "FeedbackManage.jsp")
    public void FeedbackManagePage(){
        setAttribute("lstFeedback", fbRm.findAll());
    }

}
