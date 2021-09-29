package com.ufire.authsso.model;
public class RestModel<T> {
    private Integer errorCode = 0;
    private String errorMessage;
    private T data;

    public RestModel() {
    }

    public RestModel(T data) {
        this.errorCode = 0;
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
