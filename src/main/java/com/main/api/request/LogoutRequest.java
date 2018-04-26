package com.main.api.request;

import com.main.api.model.TokenDTO;
import com.main.api.util.ApiConstants;
import org.springframework.http.HttpMethod;

public class LogoutRequest extends DataRequest<String> {

    public LogoutRequest() {
        super(ApiConstants.AUTH_REVOKE_ENDPOINT, HttpMethod.DELETE, String.class);
    }
}
