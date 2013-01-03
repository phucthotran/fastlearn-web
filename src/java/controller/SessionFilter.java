package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ITExplore
 */
@WebFilter(urlPatterns = "/*")
public class SessionFilter implements Filter {

    private ArrayList<String> studentPages;
    private ArrayList<String> adminPages;
    private ArrayList<String> facultyPages;
    private ArrayList<String> comonPages;
    private String rootPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        rootPath = filterConfig.getServletContext().getAttribute("hostURL").toString();

        String studentUrls = filterConfig.getServletContext().getInitParameter("studentPages");
        String adminUrls = filterConfig.getServletContext().getInitParameter("adminPages");
        String facultyUrls = filterConfig.getServletContext().getInitParameter("facultyPages");
        String comonUrls = filterConfig.getServletContext().getInitParameter("comonPages");

        studentPages = new ArrayList<String>();
        adminPages = new ArrayList<String>();
        facultyPages = new ArrayList<String>();
        comonPages = new ArrayList<String>();

        StringTokenizer token = new StringTokenizer(studentUrls, ",");

        while(token.hasMoreTokens()){
            studentPages.add(token.nextToken());
        }

        token = new StringTokenizer(adminUrls, ",");

        while(token.hasMoreTokens()){
            adminPages.add(token.nextToken());
        }

        token = new StringTokenizer(facultyUrls, ",");

        while(token.hasMoreTokens()){
            facultyPages.add(token.nextToken());
        }

        token = new StringTokenizer(comonUrls, ",");

        while(token.hasMoreTokens()){
            comonPages.add(token.nextToken());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        String currentURL = req.getServletPath();
        boolean allowRequest = false;

        HttpSession ss = req.getSession(false);

        if(ss != null){
            String loginType = String.valueOf(ss.getAttribute("loginType"));

            if(currentURL.equals("/User/Login")){
                if(loginType.equals("Student"))
                    resp.sendRedirect(rootPath + "/Student");
                else if(loginType.equals("Faculty"))
                    resp.sendRedirect(rootPath + "/Faculty");
                else if(loginType.equals("Admin"))
                    resp.sendRedirect(rootPath + "/admin");
            }
            chain.doFilter(request, response);
            return;
        }

        if(studentPages.contains(currentURL)){
            allowRequest = true;
        }
        else if(adminPages.contains(currentURL)){
            allowRequest = true;
        }
        else if(facultyPages.contains(currentURL)){
            allowRequest = true;
        }
        else if(comonPages.contains(currentURL)){
            allowRequest = true;
        }

        if(allowRequest){
            HttpSession s = req.getSession(false);
            
            if(s == null)
                resp.sendRedirect(rootPath + "/User/Login");
            else {
                String loginType = String.valueOf(s.getAttribute("loginType"));

                if(loginType.equals("Student") && studentPages.contains(currentURL)){                    
                }
                else if(loginType.equals("Faculty") && facultyPages.contains(currentURL)){
                }
                else if(loginType.equals("Admin") && adminPages.contains(currentURL)){
                }
                else if((loginType.equals("Student") || loginType.equals("Faculty")) && comonPages.contains(currentURL)){                    
                }
                else {
                    resp.sendRedirect(rootPath);
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
