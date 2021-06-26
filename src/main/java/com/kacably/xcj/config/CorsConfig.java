package com.kacably.xcj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class CorsConfig implements HandlerInterceptor

    {

        public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        }

        public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2)
            throws Exception {
        }

        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

            response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");//Origin, X-Requested-With, Content-Type, Accept,Access-Token
            return true;
        }


}
