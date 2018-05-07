package com.main.api.request;

import com.main.api.listener.RequestResultListener;
import com.main.api.data.AccessToken;
import com.main.api.data.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public List<Param> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<Param> requestParams) {
        this.requestParams = requestParams;
    }

    public Class<T> getResponseType() {
        return responseType;
    }

    public void setResponseType(Class<T> responseType) {
        this.responseType = responseType;
    }

    public void setRequestResultListener(RequestResultListener<T> requestResultListener) {
        this.requestResultListener = requestResultListener;
    }

    public HttpEntity getHttpEntity() {
        return new HttpEntity<>(this.requestBody, this.requestHeaders);
    }

    public abstract void setAccessToken(AccessToken accessToken);

    public boolean hasRequestListener() {
        return (this.requestResultListener != null) ? true : false;
    }

    public void setRequestListener(RequestResultListener requestResultListener) {
        this.requestResultListener = requestResultListener;
    }

    public void callRequestResult(boolean status, int statusCode, T object, String errorMessage) {
        this.requestResultListener.onRequestResult(status, statusCode, object, errorMessage);
    }

    public RequestResultListener getRequestListener() {
        return requestResultListener;
    }
}
