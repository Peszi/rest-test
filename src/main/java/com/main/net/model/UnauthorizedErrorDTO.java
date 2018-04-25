package com.main.net.model;

public class UnauthorizedErrorDTO {

    private String error;
    private String error_description;

    public void setError(String error) {
        this.error = error;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public boolean isExpired() {
        return (this.error.endsWith("invalid_token")) ? true : false;
    }
}
