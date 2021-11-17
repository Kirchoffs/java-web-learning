package org.syh.servlet.oauth2.toydemo.utils;

import java.util.Map;
import java.util.Set;

public class URLParamsUtil {
    public static String appendParams(String url, Map<String, String> params) {
        if (null == url) {
            return "";
        } else if (params.isEmpty()) {
            return url.trim();
        } else {
            StringBuilder sb = new StringBuilder();
            Set<String> keys = params.keySet();
            for (String key : keys) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);

            url = url.trim();
            int length = url.length();
            int index = url.indexOf("?");
            if (index > -1) {
                if (length - 1 == index) {
                    url += sb.toString();
                } else {
                    url += "&" + sb;
                }
            } else {
                url += "?" + sb;
            }
            return url;
        }
    }
}
