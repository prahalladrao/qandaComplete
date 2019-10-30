package com.stackoverflow.qanda.interceptor;

import com.stackoverflow.qanda.token.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class ValidateInterceptor implements HandlerInterceptor
{
    @Autowired
    TokenHandler tokenHandler;
    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token=request.getHeader("authorization").substring(7);
        System.out.println(token);
        Enumeration<String> e=request.getParameterNames();
        while(e.hasMoreElements())
            System.out.println(e.nextElement());
        Enumeration<String> en=request.getHeaderNames();
        System.out.println("headers");
        while(en.hasMoreElements())
        {
            String element=en.nextElement();
            System.out.println("element name : "+element);
            System.out.println("element value : "+request.getHeader(element));
        }
        if(tokenHandler.validate(token))
        {
            String userName=tokenHandler.getUsernameFromToken(token);
            request.setAttribute("userName",userName);
        }
        else
            return false;
        System.out.println("Pre Handle method is Calling");
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("Post Handle method is Calling");
    }
    @Override
    public void afterCompletion
            (HttpServletRequest request, HttpServletResponse response, Object
                    handler, Exception exception) throws Exception {

        System.out.println("Request and Response is completed");
    }
}
