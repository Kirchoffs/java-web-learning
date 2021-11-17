package org.syh.servlet.oauth2.toydemo;

import org.syh.servlet.oauth2.toydemo.utils.HttpURLClient;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

// http://localhost:8080/app-servlet
@WebServlet("/app-servlet")
public class AppServlet extends HttpServlet {
    private String oauthURl = "http://localhost:8080/oauth-servlet";
    private String protectedURl = "http://localhost:8080/protected-servlet";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");

        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        params.put("app_id", "APP_ID_RABBIT");
        params.put("app_secret", "APP_SECRET_RABBIT");

        System.out.println("App Servlet - receive code: " + code);

        String tokens = HttpURLClient.doPost(oauthURl, HttpURLClient.mapToStr(params));
        String accessToken = tokens.split("\\|")[0];
        String refreshToken = tokens.split("\\|")[1];
        System.out.println("App Servlet - receive accessToken: " + accessToken);
        System.out.println("App Servlet - receive refreshToken: " + refreshToken);

        Map<String, String> paramsMap = new HashMap<>();

        paramsMap.put("app_id", "APP_ID_RABBIT");
        paramsMap.put("app_secret", "APP_SECRET_RABBIT");
        paramsMap.put("token", accessToken);

        String result = HttpURLClient.doPost(protectedURl, HttpURLClient.mapToStr(paramsMap));
        System.out.println(result);
    }
}
