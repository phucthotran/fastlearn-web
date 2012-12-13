/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.fastlearn.controller.MessageFacadeRemote;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author ITExplore
 */
@WebListener
public class FastLearnContextListener implements ServletContextListener {

    @EJB
    private MessageFacadeRemote messageRm;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("hostURL", "http://localhost:8080" + context.getContextPath());
        context.setAttribute("realPath", context.getRealPath("/"));
        context.setAttribute("lstMessage", messageRm.forEverybody());

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //
    }

}
