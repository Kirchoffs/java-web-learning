package org.syh.servlet.oauth2.toydemo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.syh.servlet.oauth2.toydemo.OauthServlet.tokenMap;

// http://localhost:8080/protected-servlet
@WebServlet("/protected-servlet")
public class ProtectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken =  request.getParameter("token");
        System.out.println("Protected Servlet - token: " + accessToken);

        if (!tokenMap.containsKey(accessToken)) {
            System.out.println("Protected Servlet - token not found");
            return;
        }
        String tokenInfo = tokenMap.get(accessToken);
        long expirationTime = Long.parseLong(tokenInfo.split("\\|")[2]);
        if (expirationTime < System.currentTimeMillis()) {
            System.out.println("Protected Servlet - expired token");
            return;
        }
        System.out.println("Protected Servlet - token is valid");
        String[] scope = OauthServlet.tokenScopeMap.get(accessToken);

        StringBuilder scopeStrBuilder = new StringBuilder();
        for (int i = 0; i < scope.length; i++) {
            scopeStrBuilder.append(scope[i]);
            if (i < scope.length - 1) {
                scopeStrBuilder.append("|");
            }
        }
        String scopeStr = scopeStrBuilder.toString();
        System.out.println("Protected Servlet - scope: " + scopeStr);

        if (scopeStr.contains("today")) {
            response.getWriter().println("today!");
        }
        if (scopeStr.contains("history")) {
            response.getWriter().println("history!");
        }
        if (scopeStr.contains("pic")) {
            response.getWriter().println("pic!");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {}
}
