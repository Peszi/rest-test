package com.main.api.service;

import com.main.api.data.AccessToken;
import com.main.api.listener.AuthErrorListener;
import com.main.api.request.BaseRequest;

public interface ApiRequestFactory {
    void setAuthErrorListener(AuthErrorListener authErrorListener);
    void executeRequest(BaseRequest baseRequest);
    AccessToken getAccessToken();
}
