package com.main.data;

public class ServerErrorMessage {

    private String timestamp;
    private int status;
    private String error;
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

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
