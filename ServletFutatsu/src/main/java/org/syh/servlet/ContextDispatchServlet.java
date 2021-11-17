package org.syh.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContextDispatchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = this.getServletContext();
        System.out.println("Enter into ContextDispatchServlet");
        RequestDispatcher requestDispatcher = ctx.getRequestDispatcher("/ContextParam");
        requestDispatcher.forward(req, resp);
    }
}
