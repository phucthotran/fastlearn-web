package controller;

import anotation.TaskToPerform;
import com.fastlearn.controller.*;
import com.fastlearn.entity.*;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import model.CustomServlet;

/**
 *
 * @author ITExplore
 */
@WebServlet(name="QueryServlet", urlPatterns={"/Query/RemoveAction"})
public class QueryServlet extends CustomServlet {
   
    @EJB
    private QueryFacadeRemote queryRm;

    private String keyID = "";

    @Override
    public void processRequest(){
        HttpSession LoginSs = getRequest().getSession();
        keyID = String.valueOf(LoginSs.getAttribute("userKeyId"));
    } 

    @TaskToPerform(pathToPerform = "/Query/RemoveAction")
    public void QueryRemoveAction(){
        int id = Integer.parseInt(getParams().get("id"));

        if(id == 0){
            setResponseMessage("Not Found");
            return;
        }

        Query qr = queryRm.find(id);
        queryRm.remove(qr);

        setResponseMessage("Xóa truy vấn thành công");
    }

}
