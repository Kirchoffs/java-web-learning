package org.syh.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = this.getServletContext().getRealPath("/WEB-INF/classes/AlphonseCat.jpg");
        System.out.println(path);
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        FileInputStream in = new FileInputStream(path);
        int len;
        byte[] buffer = new byte[1024];
        ServletOutputStream out = resp.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
