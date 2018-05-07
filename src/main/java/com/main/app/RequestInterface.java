package com.main.app;

import com.main.api.data.AccessToken;
import com.main.api.model.TokenDTO;
import com.main.api.request.BaseRequest;

public interface RequestInterface {
    void sendRequest(BaseRequest baseRequest);
    TokenDTO getAccessToken();
}
