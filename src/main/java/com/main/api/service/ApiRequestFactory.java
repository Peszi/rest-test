package com.main.api.service;

import com.main.api.data.AccessToken;
import com.main.api.listener.AuthorizationListener;
import com.main.api.request.BaseRequest;

public interface ApiRequestFactory {
    void enableDebug(boolean debug);
    void setAuthorizationListener(AuthorizationListener authorizationListener);
    void executeRequest(BaseRequest baseRequest);
    AccessToken getAccessToken();
}
