package com.main.error;

public class ServerErrors {

    private String[] codes;
    private Object[] arguments;
    private String defaultMessage;
    private String objectName;
    private String field;
    private String rejectedValue;
    private boolean bindingFailure;
    private String code;

    public String[] getCodes() {
        return codes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }

    public boolean isBindingFailure() {
        return bindingFailure;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
