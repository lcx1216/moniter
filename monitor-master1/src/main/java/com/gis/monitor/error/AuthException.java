package com.gis.monitor.error;

/*
    权限异常类
 */
public class AuthException extends BaseException{
    // token验证失败
    public static final int ERROR_VERIFY_FAIL = 300;

    public AuthException(int code) {
        super(code);
    }
}