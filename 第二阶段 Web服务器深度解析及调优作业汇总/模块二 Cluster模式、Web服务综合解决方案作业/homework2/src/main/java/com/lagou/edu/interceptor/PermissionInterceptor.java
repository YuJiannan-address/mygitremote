package com.lagou.edu.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisPool jedisPool;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // String username = (String) session.getAttribute("username");
        String sessionId = getCookie(request, "REDIS_SESSION_ID");
        if (StringUtils.isEmpty(sessionId)) {
            System.out.println("没有cookie");
            response.sendRedirect("");
            return false;
        }
        System.out.println("REDIS_SESSION_ID ==> " + sessionId);
        Jedis jedis = jedisPool.getResource();
        String username = jedis.hget(sessionId, "username");

        if (!"admin" .equals(username)) {
            // 没有登录。。。跳转登录页面
            System.out.println("不存在此session--> REDIS_SESSION_ID ");
            response.sendRedirect("");
            return false;
        }
        // 重新设置redis的key有效期
        jedis.expire(sessionId, 1800);
        jedis.close();
        // 重新设置cookie有效期
        renewCookie(request, response, "REDIS_SESSION_ID", 1800);
        return true;
    }

    private String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public void renewCookie(HttpServletRequest request,
                            HttpServletResponse response,
                            String sessionId,
                            int age) {
        // 续时
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (sessionId.equals(cookie.getName())) {
                cookie.setMaxAge(age);
                response.addCookie(cookie);
            }
        }
    }
}
