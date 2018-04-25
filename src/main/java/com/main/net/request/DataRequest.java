package com.main.net.request;

import com.main.net.model.AccessToken;
import com.main.net.util.ApiConstants;
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
