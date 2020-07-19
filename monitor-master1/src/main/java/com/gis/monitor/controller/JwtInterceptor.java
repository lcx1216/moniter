package com.gis.monitor.controller;

import com.gis.monitor.pojo.basicinfo.BI_USERS;
import com.gis.monitor.service.AuthService;
import com.gis.monitor.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
    拦截器具体实现

 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthService authService;

    // 验证过程
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getParameter("token");
        if(token == null){
            response.sendRedirect(request.getContextPath()+"/auth/failure");  //验证失败
            return false;
        }
        // 通过token获取username
        String username = JwtUtil.getUsername(token);
        if(username == null){
            response.sendRedirect(request.getContextPath()+"/auth/failure");  //验证失败
            return false;
        }
        BI_USERS bi_users =  authService.getUserByUserName(username);
        // 验证信息是否正确
        if(JwtUtil.verify(token, username, bi_users.getPASSWORD())){
            return true;
        }else{
            response.sendRedirect(request.getContextPath()+"/auth/failure");  //验证失败
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
