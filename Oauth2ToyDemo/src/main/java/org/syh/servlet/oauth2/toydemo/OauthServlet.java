package org.syh.servlet.oauth2.toydemo;

import org.syh.servlet.oauth2.toydemo.utils.URLParamsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

// http://localhost:8080/oauth-servlet
@WebServlet("/oauth-servlet")
public class OauthServlet extends HttpServlet {
    static long ten_minutes = 10 * 60 * 1000;
    static long  twenty_minutes = 20 * 60 * 1000;

    // Code -> User | App | Expiration Time
    static Map<String, String> codeMap = new HashMap<>();

    // Code -> Select Scope
    static Map<String, String[]> codeScopeMap = new HashMap<>();
    static Map<String, String> tokenMap = new HashMap<>();
    static Map<String, String[]> tokenScopeMap = new HashMap<>();
    static Map<String, String> refreshTokenMap = new HashMap<>();
    static Map<String, String> appMap = new HashMap<>();
    static Map<String, String> requestIdMap = new HashMap<>();

    static {
        appMap.put("app_id", "APP_ID_RABBIT");
        appMap.put("app_secret", "APP_SECRET_RABBIT");
        appMap.put("redirect_uri", "http://localhost:8080/app-servlet");
        appMap.put("scope", "today history");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Oauth Servlet - start accept post request, generate access_token");

        String requestType = request.getParameter("request_type");

        String grantType = request.getParameter("grant_type");
        String appId = request.getParameter("app_id");
        String appSecret = request.getParameter("app_secret");

        String responseType = request.getParameter("response_type");
        String redirectUri = request.getParameter("redirect_uri");
        String scope = request.getParameter("scope");

        if ("approve".equals(requestType)) {
            String requestId = request.getParameter("request_id");

            if (!requestIdMap.containsKey(requestId)) {
                return;
            }

            if ("code".equals(responseType)) {
                String[] rscope = request.getParameterValues("rscope");
                if (!checkScope(rscope)) {
                    System.out.println("Oauth Servlet - out of scope ...");
                    return;
                }

                String code = generateCode(appId, "USER_TEST");

                codeScopeMap.put(code, rscope);

                Map<String, String> params = new HashMap<>();
                params.put("code", code);

                String toAppUrl = URLParamsUtil.appendParams(redirectUri, params);
                System.out.println("Oauth Servlet - generate code: " + code);
                System.out.println("Oauth Servlet - redirect to: " + toAppUrl);
                response.sendRedirect(toAppUrl);
            }
        }

        if ("authorization_code".equals(grantType)) {
            if (!appMap.get("app_id").equals(appId)) {
                response.getWriter().write("app_id is not available");
                return;
            }

            if (!appMap.get("app_secret").equals(appSecret)) {
                response.getWriter().write("app_secret is not available");
                return;
            }

            String code = request.getParameter("code");

            if (!isCodeValid(code)) {
                return;
            }
            codeMap.remove(code);

            System.out.println("Oauth Servlet - start generate access_token");
            String accessToken = generateAccessToken(appId, "USER_TEST");
            tokenMap.put(accessToken, appId + "|" + "USER_TEST" + "|" + (System.currentTimeMillis() + ten_minutes));
            tokenScopeMap.put(accessToken, codeScopeMap.get(code));

            String refreshToken = generateRefreshToken(appId, "USER_TEST");
            refreshTokenMap.put(refreshToken, appId + "|" + "USER_TEST" + "|" + (System.currentTimeMillis() + twenty_minutes));

            System.out.println(
                String.format("Oauth Servlet - access token: %s refresh token: %s", accessToken, refreshToken)
            );

            response.getWriter().write(accessToken + "|" + refreshToken);
        } else if ("refresh_token".equals(grantType)) {
            if (!"APP_ID_TEST".equals(appId)) {
                response.getWriter().write("app_id is not available");
                return;
            }

            if (!"APP_SECRET_TEST".equals(appSecret)) {
                response.getWriter().write("app_secret is not available");
                return;
            }

            String refreshToken = request.getParameter("refresh_token");

            if (!refreshTokenMap.containsKey(refreshToken)) {
                return;
            }

            String appStr = refreshTokenMap.get(refreshToken);
            if(!appStr.startsWith(appId + "|" + "USER-TEST")){
                return;
            }

            String accessToken = generateAccessToken(appId,"USER_TEST");
            tokenMap.put(accessToken, appId + "|" + "USER_TEST" + "|" + (System.currentTimeMillis() + ten_minutes));

            response.getWriter().write(accessToken);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String responseType = request.getParameter("response_type");
        String redirectUri =request.getParameter("redirect_uri");
        String appId = request.getParameter("app_id");
        String scope = request.getParameter("scope");

        if (!appMap.get("app_id").equals(appId)) {
            return;
        }

        if (!appMap.get("redirect_uri").equals(redirectUri)) {
            return;
        }

        if (!checkScope(scope)) {
            return;
        }

        String requestId = String.valueOf(System.currentTimeMillis());
        requestIdMap.put(requestId, requestId);

        request.setAttribute("request_id", requestId);
        request.setAttribute("response_type", responseType);
        request.setAttribute("redirect_uri", redirectUri);
        request.setAttribute("app_id", appId);

        request.getRequestDispatcher("/approve.jsp").forward(request, response);
    }

    private boolean checkScope(String[] rscope){
        for (int i = 0; i < rscope.length; i++) {
            if (!appMap.get("scope").contains(rscope[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean checkScope(String scope){
        return appMap.get("scope").contains(scope);
    }

    private String generateCode(String appId, String user) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(r.nextInt(10));
        }

        String code = sb.toString();

        codeMap.put(code, appId + "|" + user + "|" + (System.currentTimeMillis() + ten_minutes));
        return code;
    }

    private boolean isCodeValid(String code) {
        return codeMap.containsKey(code);
    }

    private String generateAccessToken(String appId, String user) {
        String accessToken = UUID.randomUUID().toString();
        return accessToken;
    }

    private String generateRefreshToken(String appId, String user) {
        String refreshToken = UUID.randomUUID().toString();
        return refreshToken;
    }
}
