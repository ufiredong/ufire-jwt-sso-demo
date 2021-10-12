package com.ufire.authsso.model;

import lombok.Data;

@Data
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
}
