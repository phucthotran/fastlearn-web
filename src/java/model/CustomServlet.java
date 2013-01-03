package model;

import anotation.TaskToPerform;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ITExplore
 */
public class CustomServlet extends HttpServlet implements IRequest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter print;
    private String forwardPage;
    private HashMap<String, String> params;
    private String responseMessage;

    public HttpServletRequest getRequest(){
        return request;
    }

    public HttpServletResponse getResponse(){
        return response;
    }

    public PrintWriter getPrint(){
        return print;
    }

    public HashMap<String, String> getParams(){
        return params;
    }

    public void setResponseMessage(String responseMessage){
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage(){
        return responseMessage;
    }

    public void setAttribute(String name, Object value){
        request.setAttribute(name, value);
    }

    public String getServletPath(){
        return request.getServletPath();
    }

    @Override
    public void init(){
    }
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{        
        response.setContentType("text/html; charset=UTF-8");
        this.request = request;
        this.response = response;
        this.print = response.getWriter();

        if(!response.isCommitted())
            super.service(request, response);
    }
    
    @Override
    public void processRequest(){
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{       
        beforeProcessRequest();
        processRequest();
        inProcessRequest();
        afterProcessRequest();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        beforeProcessRequest();
        processRequest();
        inProcessRequest();
        afterProcessRequest();
    }

    public void beforeProcessRequest(){
        getParamsFromRequest();
    }

    public void inProcessRequest(){
        try {
            performTask(this);
        } catch (InstantiationException ex) {
            Logger.getLogger(CustomServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CustomServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CustomServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CustomServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(CustomServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void afterProcessRequest() throws ServletException, IOException{
        if(responseMessage != null) {
            print.write(responseMessage);
        }

        if(!forwardPage.equals(""))
            request.getRequestDispatcher(forwardPage).forward(request, response);
    }

    private void getParamsFromRequest(){
        params = new HashMap<String, String>();

        ArrayList<String> propertyNames = new ArrayList<String>();
        ArrayList<String> propertyValues = new ArrayList<String>();

        for(String name : request.getParameterMap().keySet()) {
            propertyNames.add(name);
        }

        for(String[] values : request.getParameterMap().values()) {
            for(String value : values) {
                propertyValues.add(value);
            }
        }

        for(int i = 0; i < propertyNames.size() && i < propertyValues.size(); i++){
            params.put(propertyNames.get(i), propertyValues.get(i));
        }
    }

    public void performTask(Object Tasks) throws InstantiationException, IllegalArgumentException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method[] methods = Tasks.getClass().getMethods();

        for(Method m : methods){
            if(m.isAnnotationPresent(TaskToPerform.class)){
                TaskToPerform task = (TaskToPerform)m.getAnnotation(TaskToPerform.class);
                String performPath = task.pathToPerform();
                String forwardPath = task.forwardPage();

                if(performPath.equals(getServletPath())){
                    this.forwardPage = forwardPath;
                    m.invoke(Tasks, new Object[]{});
                }
            }
        }
    }
}