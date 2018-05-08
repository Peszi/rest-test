package com.main.api.listener;

import com.main.api.data.RequestStatus;

public interface RequestResultListener<T> {
    void onRequestResult(RequestStatus<T> requestStatus);
}
