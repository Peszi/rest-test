package com.main.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthErrorMessageDTO {

    private String error;
    private String error_description;
    private String message;

    public void setError(String error) {
        this.error = error;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorDescription() {
        return error_description;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAccessTokenInvalid() {
        return (this.error != null && this.error.endsWith("invalid_token")) ? true : false;
    }

    public boolean isRefreshTokenInvalid() {
        return (this.error != null && this.error.endsWith("invalid_grant")) ? true : false;
    }
}
