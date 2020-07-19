package com.gis.monitor.error;

/*
    用户异常类
 */
public class UserException extends BaseException {

    // 用户不存在
    public static final int ERROR_USER_NOT_EXIT = 700;
    // 密码错误
    public static final int ERROR_USER_PASSWORD_EEEOR = 701;
    // 输入用户名
    public static final int ERROR_NOT_HAS_USERNAME = 702;
    // 输入密码
    public static final int ERROR_NOT_HAS_PASSWORD = 703;

    public UserException(int code) {
        super(code);
    }
}
