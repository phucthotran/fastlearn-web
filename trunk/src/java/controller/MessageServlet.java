package controller;

import com.fastlearn.controller.MessageFacadeRemote;
import com.fastlearn.entity.Message;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private String pathToPerform;
    private String forwardPage;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pathToPerform = request.getServletPath();
        response.getWriter();
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        if(pathToPerform.equals("/admin/MessageManage")) {
            request.setAttribute("lstMessage", messageRm.findAll());
            forwardPage = "MessageManage.jsp";
        }
        else if(pathToPerform.equals("/admin/Message/Post")) {
            forwardPage = "../PostMessage.jsp";
        }
        else if(pathToPerform.equals("/admin/Message/Edit")) {
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
        else if(pathToPerform.equals("/admin/Message/Remove")) {
            int id = Integer.parseInt(request.getParameter("id"));

            if(id == 0) {
                setMessage("Wrong URL");
                request.setAttribute("message", message);
                forwardPage = "../../do/error.jsp";
            }
            else {
                //messageRm.remove(id);
                setMessage("Remove message succcess");
                request.setAttribute("message", message);
                forwardPage = "../../do/success.jsp";
            }
        }
        else if(pathToPerform.equals("/admin/Message/Publish") || pathToPerform.equals("/admin/Message/Unpublish")) {
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

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        if(pathToPerform.equals("/admin/Message/PostAction")) {
            String title = request.getParameter("title");
            String message_ = request.getParameter("message");
            int type = Integer.parseInt(request.getParameter("type"));

            Message newMessage = new Message(title, message_, type);
            messageRm.insert(newMessage);

            setMessage("Post message success");
            request.setAttribute("message", message);
            forwardPage = "../../do/success.jsp";
        }
        else if(pathToPerform.equals("/admin/Message/EditAction")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String message_ = request.getParameter("message");
            int type = Integer.parseInt(request.getParameter("type"));

            Message editMessage = messageRm.find(id);
            editMessage.setTitle(title);
            editMessage.setMessage(message_);
            editMessage.setType(type);

            messageRm.update(editMessage);

            setMessage("Edit Success");
            request.setAttribute("message", message);

            forwardPage = "../../do/success.jsp";
        }

        request.getRequestDispatcher(forwardPage).forward(request, response);
    }

}
