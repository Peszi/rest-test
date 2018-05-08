package com.main.app;

import com.main.api.data.AccessToken;
import com.main.api.request.BaseRequest;

public interface RestClient {
    void enableDebug(boolean enable);
    void sendRequest(BaseRequest request);
    void sendRequestForResult(BaseRequest request);
    AccessToken getAccessToken();
}
