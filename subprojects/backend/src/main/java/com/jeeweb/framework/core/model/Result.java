/**
 * 
 */
package com.jeeweb.framework.core.model;

/**
 * @author 袁进勇
 *
 */
public class Result {
    public static final String SUCCESS_MESSAGE = "处理成功。";
    private Boolean success; // 处理结果
    private String message; // 结果描述
    private Object data; // 结果数据

    public Result() {
        this(true, SUCCESS_MESSAGE, null);
    }

    public Result(Object data) {
        this(true, SUCCESS_MESSAGE, data);
    }

    public Result(String message) {
        this(false, message, null);
    }

    public Result(Boolean success, String message) {
        this(success, message, null);
    }

    public Result(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }
    
    public void setSuccess(Boolean success) {
        this.success = success;
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
}
