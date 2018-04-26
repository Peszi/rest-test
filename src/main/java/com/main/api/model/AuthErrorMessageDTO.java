package com.main.api.model;

public class AuthErrorMessageDTO {

    private String error;
    private String error_description;

    public void setError(String error) {
        this.error = error;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getErrorDescription() {
        return error_description;
    }

    public boolean isAccessTokenExpired() {
        return (this.error != null && this.error.endsWith("invalid_token")) ? true : false;
    }

    public boolean isRefreshTokenInvalid() {
        return (this.error != null && this.error.endsWith("invalid_grant")) ? true : false;
    }
}
