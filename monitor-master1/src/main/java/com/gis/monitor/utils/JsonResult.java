package com.gis.monitor.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Objects;

/*
    数据统一返回
 */
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 5598308977637490090L;
    // 状态码
    private int status;
    // 成功信息
    private String message;
    // 返回数据
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    // 处理成功 无返回值
    public static JsonResult success(){
        return success(null);
    }
    // 处理成功 包含返回值
    public static JsonResult success(Object data){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(200);
        jsonResult.setMessage("SUCCESS");
        if(data != null){
            jsonResult.setData(data);
        }
        return jsonResult;
    }
    // 处理失败 无信息
    public static JsonResult failed() {
        return failed("FAILED");
    }
    // 处理成功 包含提示信息
    public static JsonResult failed(String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(500);
        jsonResult.setMessage(message);
        return jsonResult;
    }
}
