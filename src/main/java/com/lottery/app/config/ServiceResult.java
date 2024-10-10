package com.lottery.app.config;

import java.io.Serializable;

public class ServiceResult<T> implements Serializable {

    private String status;
    private String message;
    private T data;
    private String code;

    public ServiceResult() {}

    public ServiceResult(T data, String status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public ServiceResult(String status, String message, T data, String code) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
