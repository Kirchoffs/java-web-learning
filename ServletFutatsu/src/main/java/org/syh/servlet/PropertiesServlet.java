package org.syh.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Tomcat Directory = " + System.getProperty("user.dir"));
        System.out.println("Working Directory = " + this.getServletContext().getRealPath("/"));
        InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");
        Properties properties = new Properties();
        properties.load(is);
        String user = properties.getProperty("username");
        String password = properties.getProperty("password");
        resp.getWriter().print(user + ": " + password);
    }
}
