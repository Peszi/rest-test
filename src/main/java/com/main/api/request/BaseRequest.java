package com.main.api.request;

import com.main.api.data.RequestStatus;
import com.main.api.listener.RequestResultListener;
import com.main.api.data.AccessToken;
import com.main.api.data.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRequest<T> {

    private String endpoint;
    private HttpMethod requestMethod;
    private HttpHeaders requestHeaders;
    private List<Param> requestParams;
    private String requestBody;
    private Class<T> responseType;

    private RequestResultListener<T> requestResultListener;

    public BaseRequest(String endpoint, HttpMethod requestMethod, Class<T> responseType) {
        this.endpoint = endpoint;
        this.requestMethod = requestMethod;
        this.requestHeaders = new HttpHeaders();
        this.requestParams = new ArrayList<>();
        this.requestBody = "";
        this.responseType = responseType;
    }

    public HttpMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(HttpMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getURL(String serverIp) {
        UriComponentsBuilder requestUrl = UriComponentsBuilder.fromHttpUrl(serverIp + this.endpoint);
        for (Param param : this.requestParams)
            requestUrl.queryParam(param.getKey(), param.getValue());
        return requestUrl.toUriString();
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public HttpHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(HttpHeaders requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public void addParameter(Param param) {
        this.requestParams.add(param);
    }

    public Class<T> getResponseType() {
        return responseType;
    }

    public void setResponseType(Class<T> responseType) {
        this.responseType = responseType;
    }

    public HttpEntity getHttpEntity() {
        return new HttpEntity<>(this.requestBody, this.requestHeaders);
    }

    public abstract void setAccessToken(AccessToken accessToken);

    public boolean hasRequestListener() {
        return (this.requestResultListener != null) ? true : false;
    }

    public void setRequestListener(RequestResultListener<T> requestResultListener) {
        this.requestResultListener = requestResultListener;
    }

    public void callResultListener(RequestStatus<T> requestStatus) {
        if (this.requestResultListener != null)
            this.requestResultListener.onRequestResult(requestStatus);
    }
}
