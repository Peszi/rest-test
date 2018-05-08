package com.main.api.data;

public class RequestStatus<T> {

    private boolean status;
    private int statusCode;
    private T object;
    private String errorMessage;

    public RequestStatus(boolean status, int statusCode, String errorMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage.trim();
    }

    public RequestStatus(boolean status, int statusCode, T object) {
        this.status = status;
        this.statusCode = statusCode;
        this.object = object;
        this.errorMessage = "";
    }

    public RequestStatus(boolean status, int statusCode, T object, String errorMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.object = object;
        this.errorMessage = errorMessage.trim();
    }

    public boolean isStatusOK() {
        return status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getObject() {
        return object;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
