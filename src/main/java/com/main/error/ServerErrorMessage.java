package com.main.error;

public class ServerErrorMessage {

    private String timestamp;
    private int status;
    private String error;
    private ServerErrors[] errors;
    private String message;
    private String path;

    public ServerErrorMessage() {}

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public ServerErrors[] getErrors() {
        return errors;
    }

    public String getMessage() {
        if (errors != null && errors.length == 1)
            return errors[0].getDefaultMessage();
        return message;
    }

    public String getPath() {
        return path;
    }
}
