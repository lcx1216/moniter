package com.gis.monitor.controller;

import com.gis.monitor.error.AuthException;
import com.gis.monitor.error.BaseException;
import com.gis.monitor.error.UserException;
import com.gis.monitor.pojo.basicinfo.BI_USERS;
import com.gis.monitor.service.AuthService;
import com.gis.monitor.utils.JsonResult;
import com.gis.monitor.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/*
    验证控制器
    用于登陆
    返回 token
 */

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController{
    @Autowired
    private AuthService authService;
    // 登陆
    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(@RequestParam String USERNAME,
                            @RequestParam String PASSWORD) throws UserException, BaseException{
        String token =  authService.Login(USERNAME, PASSWORD);
        if(token != null){
            return JsonResult.success(token);
        }else{
            throw new UserException(UserException.ERROR_USER_PASSWORD_EEEOR);
        }
    };
    @GetMapping("/info")
    @ResponseBody
    public JsonResult info(HttpServletRequest request){
        String token = request.getParameter("token");
        String username = JwtUtil.getUsername(token);
        BI_USERS bi_users = authService.getUserByUserName(username);
        return JsonResult.success(bi_users);
    }
    // 失败返回
    @GetMapping("/failure")
    @ResponseBody
    public JsonResult failure(HttpServletRequest request) throws UserException, BaseException {
       throw new AuthException(AuthException.ERROR_VERIFY_FAIL);
    }
    // test
    @PostMapping("/aaa")
    @ResponseBody
    public JsonResult aaa(HttpServletRequest request){
            return JsonResult.success("1");
    }

}
