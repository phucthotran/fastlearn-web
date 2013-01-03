package controller;

import anotation.TaskToPerform;
import com.fastlearn.controller.MessageFacadeRemote;
import com.fastlearn.entity.Message;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CustomServlet;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="MessageServlet", urlPatterns={
    "/admin/MessageManage",
    "/admin/Message/PostAction",
    "/admin/Message/Edit",
    "/admin/Message/UpdateAction",
    "/admin/Message/Remove",
    "/admin/Message/Publish",
    "/admin/Message/Unpublish"
})
public class MessageServlet extends CustomServlet {

    @EJB
    private MessageFacadeRemote messageRm;

    private String keyID = "";
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        HttpSession LoginSs = request.getSession();

        keyID = String.valueOf(LoginSs.getAttribute("userKeyId"));
    }

    @TaskToPerform(pathToPerform = "/admin/MessageManage", forwardPage = "MessageManage.jsp")
    public void MessageManagePage(){
        setAttribute("lstMessage", messageRm.findAll());
    }

    @TaskToPerform(pathToPerform = "/admin/Message/PostAction")
    public void PostMessageAction(){
        String title = getParams().get("title");
        String message = getParams().get("message");
        int type = Integer.parseInt(getParams().get("type"));

        Message newMessage = new Message(title, message, type);
        messageRm.insert(newMessage);

        setResponseMessage("Đăng thông báo thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/Message/Edit", forwardPage = "../EditMessage.jsp")
    public void EditMessage(){
        int id = Integer.parseInt(getParams().get("id"));

        if(id == 0) {
            setResponseMessage("Not Found");
            return;
        }
        
        Message editMessage = messageRm.find(id);
        setAttribute("message", editMessage);
    }

    @TaskToPerform(pathToPerform = "/admin/Message/UpdateAction")
    public void EditMessageAction(){
        int id = Integer.parseInt(getParams().get("messageID"));
        String title = getParams().get("title");
        String message = getParams().get("message");
        int type = Integer.parseInt(getParams().get("type"));

        Message editMessage = messageRm.find(id);
        editMessage.setTitle(title);
        editMessage.setMessage(message);
        editMessage.setType(type);

        messageRm.update(editMessage);

        setResponseMessage("Sửa thông báo thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/Message/Remove")
    public void RemoveMessage(){
        int id = Integer.parseInt(getParams().get("id"));

        if(id == 0) {
            setResponseMessage("Not Found");
            return;
        }
        
        messageRm.remove(id);
        setResponseMessage("Xóa thông báo thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/Message/Publish")
    public void PublishUnpublishMessage(){
        int id = Integer.parseInt(getParams().get("id"));

        if(id == 0) {
            setResponseMessage("Not Found");
            return;
        }
                  
        messageRm.publish(id);
        setResponseMessage("Hiệu lực thông báo thành công");
    }

    @TaskToPerform(pathToPerform = "/admin/Message/Unpublish")
    public void UnpublishMessage(){
        int id = Integer.parseInt(getParams().get("id"));

        if(id == 0) {
            setResponseMessage("Not Found");
            return;
        }

        messageRm.unpublish(id);
        setResponseMessage("Ẩn thông báo thành công");
    }

}
