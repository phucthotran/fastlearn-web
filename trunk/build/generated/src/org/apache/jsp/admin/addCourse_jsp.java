package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class addCourse_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.apache.jasper.runtime.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.apache.jasper.runtime.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Add Course</title>\n");
      out.write("        <script type=\"text/javascript\" src=\"../js/jquery.js\"></script>\n");
      out.write("        <script type=\"text/javascript\">\n");
      out.write("            $(function(){\n");
      out.write("                $('#btnEdit').click(function(){\n");
      out.write("                    var name = $(\"input[name=name]\").val();\n");
      out.write("                    var fee = $(\"input[name=fee]\").val();\n");
      out.write("                    var prerequisites = $(\"input[name=prerequisites]\").val();\n");
      out.write("\n");
      out.write("                    $.ajax({\n");
      out.write("                        type: 'POST',\n");
      out.write("                        url: '../AddCourse',\n");
      out.write("                        dataType: 'html',\n");
      out.write("                        data: { name : name, fee : fee,  prerequisites : prerequisites},\n");
      out.write("                        success: function(data) {\n");
      out.write("                            $('#result').html(data);\n");
      out.write("                        }\n");
      out.write("                    });\n");
      out.write("                });\n");
      out.write("            });\n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Hello World!</h1>\n");
      out.write("        <form method=\"POST\">\n");
      out.write("            <p>Name: <input type=\"text\" name=\"name\"/></p>\n");
      out.write("            <p>Fee <input type=\"text\" name=\"fee\"/></p>\n");
      out.write("            <p>Prerequisites: <input type=\"text\" name=\"prerequisites\"/></p>\n");
      out.write("            <p><input type=\"button\" id=\"btnAdd\" value=\"Add\"/></p>\n");
      out.write("            <div id=\"result\"></div>\n");
      out.write("        </form>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
