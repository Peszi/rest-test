package com.main.net.model;

import com.main.net.callback.RequestResultInterface;

public class RequestEntry {

    private RequestResultInterface requestResultInterface;

    public void setRequestResultInterface(RequestResultInterface requestResultInterface) {
        this.requestResultInterface = requestResultInterface;
    }

    public RequestResultInterface getRequestResultInterface() {
        return requestResultInterface;
    }
}
