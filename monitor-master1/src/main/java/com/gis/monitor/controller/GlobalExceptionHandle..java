//package com.gis.monitor.controller;
//
//import com.gis.monitor.error.BaseException;
//import com.gis.monitor.utils.JsonResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.NoSuchMessageException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Locale;
//
///*
//    全局错误处理
// */
//
//@ControllerAdvice
//public class GlobalExceptionHandle {
//    private final MessageSource messageSource;
//
//    @Autowired
//    public GlobalExceptionHandle(MessageSource messageSource) {
//        this.messageSource = messageSource;
//    }
//
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public JsonResult defaultErrorHandle(HttpServletRequest request, HttpServletResponse response,
//                                         Exception e, Locale locale) throws IOException {
//            JsonResult jsonResult = new JsonResult();
//            int code = 500;
//            String errorMessage = null;
//            Throwable exception = e;
//            // 判断是否未基础错误
//            if(exception instanceof BaseException){
//                code = ((BaseException) exception).getCode();
//                errorMessage = ((BaseException)exception).getErrorMessage();
//            }
//            // 判断是否未缺少参数错误
//            if(exception instanceof MissingServletRequestParameterException){
//                code = BaseException.ERROR_CODE_ILLEGAL_ARGUMENTS;
//            }
//            if (errorMessage == null) {
//                errorMessage = getMessage(Integer.toString(code), locale);
//            }
//            jsonResult.setStatus(code);
//            jsonResult.setMessage(errorMessage);
//            return jsonResult;
//    }
//
//    private String getMessage(String key, Locale locale) {
//        String errorMessage = null;
//        try {
//            // 从本地文件获取错误信息
//            errorMessage = messageSource.getMessage(key, null, locale);
//        } catch (NoSuchMessageException exception) {
//            System.out.println("not found key" + key);
//        }
//        return errorMessage;
//    }
//}
