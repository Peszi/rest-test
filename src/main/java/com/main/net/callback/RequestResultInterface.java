package com.main.net.callback;

public interface RequestResultInterface<T> {
    void onRequestResult(boolean status, int statusCode, T object);
}
