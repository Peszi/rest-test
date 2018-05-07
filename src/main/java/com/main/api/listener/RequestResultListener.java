package com.main.api.listener;

public interface RequestResultListener<T> {
    void onRequestResult(boolean status, int statusCode, T object, String errorMessage);
}
