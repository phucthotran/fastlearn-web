package controller;

import anotation.MDO;
import com.fastlearn.controller.MessageFacadeRemote;
import com.fastlearn.entity.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import library.ModelDriven;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="MessageServlet", urlPatterns={
    "/admin/MessageManage",
    "/admin/Message/Post",
    "/admin/Message/PostAction",
    "/admin/Message/Edit",
    "/admin/Message/EditAction",
    "/admin/Message/Remove",
    "/admin/Message/Publish",
    "/admin/Message/Unpublish"
})
public class MessageServlet extends HttpServlet {

    @EJB
    private MessageFacadeRemote messageRm;

    @MDO
    private Message msg;

    private String pathToPerform;
    private String forwardPage;

    private PrintWriter out = null;

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    private String keyID = "";
    private String loginType = "";
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pathToPerform = request.getServletPath();
        response.setContentType("text/plain; charset=UTF-8");
        out = response.getWriter();
        this.request = request;
        this.response = response;

        HttpSession LoginSs = request.getSession();

        keyID = (String)LoginSs.getAttribute("userKeyId");
        loginType = (String)LoginSs.getAttribute("loginType");

        if(keyID.equals("") && loginType.equals("")) {
            String hostURL = request.getServletContext().getAttribute("hostURL").toString();
            response.sendRedirect(hostURL + "/login.jsp");
        }
    }

    //TYPE : GET
    public void MessageManagePage(){
        request.setAttribute("lstMessage", messageRm.findAll());
        forwardPage = "MessageManage.jsp";
    }

    public void PostMessage(){
        forwardPage = "../PostMessage.jsp";
    }

    public void EditMessage(){
        int id = Integer.parseInt(request.getParameter("id"));

        if(id == 0) {
            setMessage("Wrong URL");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            Message editMessage = messageRm.find(id);
            request.setAttribute("message", editMessage);
            forwardPage = "../EditMessage.jsp";
        }
    }

    public void RemoveMessage(){
        int id = Integer.parseInt(request.getParameter("id"));

        if(id == 0) {
            setMessage("Wrong URL");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            messageRm.remove(id);
            setMessage("Remove message succcess");
            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
    }

    public void PublishUnpublishMessage(){
        int id = Integer.parseInt(request.getParameter("id"));

        if(id == 0) {
            setMessage("Wrong URL");
            request.setAttribute("message", message);
            forwardPage = "../../do/error.jsp";
        }
        else {
            if(pathToPerform.equals("/admin/Message/Publish")) {
                messageRm.publish(id);
                setMessage("Publish message success");
            }
            else if(pathToPerform.equals("/admin/Message/Unpublish")) {
                messageRm.unpublish(id);
                setMessage("Unpublish message success");
            }

            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
    }

    //TYPE : POST
    public void PostMessageAction(){
        //String title = request.getParameter("title");
        //String message_ = request.getParameter("message");
        //int type = Integer.parseInt(request.getParameter("type"));

        //Message newMessage = new Message(title, message_, type);
        //messageRm.insert(newMessage);

        messageRm.insert(msg);

        setMessage("Post message success");
        request.setAttribute("message", message);
        forwardPage = "../../do/success.jsp";
    }

    public void EditMessageAction(){
        //int id = Integer.parseInt(request.getParameter("id"));
        //String title = request.getParameter("title");
        //String message_ = request.getParameter("message");
        //int type = Integer.parseInt(request.getParameter("type"));

        Message editMessage = messageRm.find(msg.getMessage());
        editMessage.setTitle(msg.getTitle());
        editMessage.setMessage(msg.getMessage());
        editMessage.setType(msg.getType());

        messageRm.update(editMessage);

        setMessage("Edit Success");
        request.setAttribute("message", message);

        forwardPage = "../../do/success.jsp";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(!loginType.equals("Admin"))
            return;

        if(pathToPerform.equals("/admin/MessageManage")) {
            MessageManagePage();
        }
        else if(pathToPerform.equals("/admin/Message/Post")) {
            PostMessage();
        }
        else if(pathToPerform.equals("/admin/Message/Edit")) {
            EditMessage();
        }
        else if(pathToPerform.equals("/admin/Message/Remove")) {
            RemoveMessage();
        }
        else if(pathToPerform.equals("/admin/Message/Publish") || pathToPerform.equals("/admin/Message/Unpublish")) {
            PublishUnpublishMessage();
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(!loginType.equals("Admin"))
            return;

        ModelDriven.setRequest(request);

        try {
            ModelDriven.parser(MessageServlet.class);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MessageServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MessageServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MessageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(pathToPerform.equals("/admin/Message/PostAction")) {
            PostMessageAction();
        }
        else if(pathToPerform.equals("/admin/Message/EditAction")) {
            EditMessageAction();
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

}
