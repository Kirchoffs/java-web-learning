package org.syh.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CountListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext ctx = se.getSession().getServletContext();

        Integer oldOnlineCount = (Integer) ctx.getAttribute("OnlineCount");
        int onlineCount;
        if (oldOnlineCount == null) {
            onlineCount = 1;
        } else {
            onlineCount = oldOnlineCount + 1;
        }
        ctx.setAttribute("OnlineCount", onlineCount);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext ctx = se.getSession().getServletContext();

        Integer oldOnlineCount = (Integer) ctx.getAttribute("OnlineCount");
        int onlineCount;
        if (oldOnlineCount == null) {
            onlineCount = 0;
        } else {
            onlineCount = Math.max(0, oldOnlineCount - 1);
        }
        ctx.setAttribute( "OnlineCount", onlineCount);
    }
}
