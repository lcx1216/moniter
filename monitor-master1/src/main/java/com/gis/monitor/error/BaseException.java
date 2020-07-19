package com.gis.monitor.error;

/*
    基础异常类
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = -3392027101671055457L;
    // 参数错误代码
    public static final int ERROR_CODE_ILLEGAL_ARGUMENTS = 600;
    // 错误代码
    private int code;
    // 错误信息
    private String errorMessage;

    public BaseException(int code) {
        super("error code" + code);
        this.code = code;
    }

    public BaseException(int code, Throwable throwable) {
        super(throwable);
        this.code = code;

    }

    public BaseException(int code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
