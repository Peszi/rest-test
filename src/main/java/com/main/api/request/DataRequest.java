package com.main.api.request;

import com.main.api.data.AccessToken;
import com.main.api.util.ApiConstants;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class DataRequest<T> extends BaseRequest<T> {

    public DataRequest(String path, HttpMethod requestMethod, Class<T> responseType) {
        super(path, requestMethod, responseType);
        this.getRequestHeaders().set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @Override
    public void setAccessToken(AccessToken accessToken) {
        this.getRequestHeaders().set("Authorization", ApiConstants.BEARER_AUTH + accessToken.getUserToken().getAccessToken());
    }
}
