package org.syh.servlet.oauth2.toydemo;

import org.syh.servlet.oauth2.toydemo.utils.URLParamsUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// http://localhost:8080/app-index-servlet
@WebServlet("/app-index-servlet")
public class AppIndexServletDemo extends HttpServlet {
    private String oauthUrl = "http://localhost:8080/oauth-servlet?reqType=oauth";
    private String redirectUrl = "http://localhost:8080/app-servlet";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("redirect_uri", redirectUrl);
        params.put("app_id", "APP_ID_RABBIT");
        params.put("scope", "today history");

        String toOauthUrl = URLParamsUtil.appendParams(oauthUrl, params);

        System.out.println(String.format("App Index Servlet - redirect to oauth service: \"%s\"", toOauthUrl));

        response.sendRedirect(toOauthUrl);
    }
}
