package com.wx.app.game.commom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName HttpRequestUtils
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-21 21:54
 * @Version 1.0
 */
@Component
public class HttpRequestUtils {

    @Autowired
    private HttpServletResponse response;

    public HttpServletRequest getRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        return servletRequestAttributes.getRequest();
    }

    public HttpServletResponse getResponse(){
        return this.response;
    }
}
