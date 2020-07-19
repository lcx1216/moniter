package com.gis.monitor.controller;

import com.gis.monitor.error.AuthException;
import com.gis.monitor.error.BaseException;
import com.gis.monitor.error.UserException;
import com.gis.monitor.utils.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
/*
    测试URL
 */
@RestController
@RequestMapping("/")
public class whatController {
    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(HttpServletRequest request) throws UserException, BaseException {
        return JsonResult.success("1");
    };
    @GetMapping("/failure")
    @ResponseBody
    public JsonResult sss(HttpServletRequest request) throws UserException, BaseException {
        return JsonResult.success("2");
    }
    @PostMapping("/aaa")
    @ResponseBody
    public JsonResult aaa(HttpServletRequest request){
        return JsonResult.success("3");
    }
}
