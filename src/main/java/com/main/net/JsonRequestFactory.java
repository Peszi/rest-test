package com.main.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.net.model.Param;
import com.main.error.ServerErrorMessage;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class JsonRequestFactory {

    private String serverIp;
    private String basicAuth;

    private RestTemplate restTemplate;
    private HttpEntity<?> httpEntity;
    private ObjectMapper jsonMapper;

    public JsonRequestFactory(String serverIp) {
        this.serverIp = serverIp;
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        this.httpEntity = new HttpEntity<>(this.getHttpHeaders());
        this.jsonMapper = new ObjectMapper();
    }

    private String getBasicAuthString(String clientId, String secret) {
        final byte[] base64 = new String(clientId + ":" + secret).getBytes();
        return "Basic " + new String(Base64.getEncoder().encode(base64));
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set("Accept-Language", "pl-PL");
        return httpHeaders;
    }

//    public <T> HttpStatus makeRequestForResult(String path, HttpMethod method, Class<T> responseType, Param... params) {
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.serverIp + path);
//        Arrays.stream(params).forEach(param -> builder.queryParam(param.getKey(), param.getValue()));
//        return this.makeRequest(method, builder, responseType).getStatusCode();
//    }

    public ResponseObject<String> makeRequest(String path, HttpMethod method, Param... params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.serverIp + path);
        Arrays.stream(params).forEach(param -> builder.queryParam(param.getKey(), param.getValue()));
        return this.makeRequest(method, builder, String.class);
    }

    public <T> ResponseObject<T> makeRequest(String path, HttpMethod method, Class<T> responseType, Param... params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.serverIp + path);
        Arrays.stream(params).forEach(param -> builder.queryParam(param.getKey(), param.getValue()));
        return this.makeRequest(method, builder, responseType);
    }

    public <T> ResponseObject<T> makeRequest(String path, HttpMethod method, Class<T> responseType, Map<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.serverIp + path);
        params.forEach((k, v) -> builder.queryParam(k, v));
        return this.makeRequest(method, builder, responseType);
    }

    private <T> ResponseObject<T> makeRequest(HttpMethod method, UriComponentsBuilder componentsBuilder, Class<T> responseType) {
        ResponseObject<T> responseEntity = new ResponseObject<>();
        try {
            responseEntity.setResponseObject(this.restTemplate.exchange(componentsBuilder.toUriString(), method, this.httpEntity, responseType));
        } catch (HttpClientErrorException e) {
            responseEntity.setResponseError(this.getErrorResponse(e));
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.err.println("Build request factory first!");
        }
        return responseEntity;
    }

    public ServerErrorMessage getErrorResponse(HttpClientErrorException e) {
        try {
            return this.jsonMapper.readValue(e.getResponseBodyAsString(), ServerErrorMessage.class);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
