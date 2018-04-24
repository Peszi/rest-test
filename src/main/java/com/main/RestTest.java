package com.main;

import com.main.net.model.AccessToken;
import com.main.net.model.RequestEntry;
import com.main.net.model.TokenDTO;
import com.main.net.util.ApiConstants;
import com.main.net.util.ApiUtil;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RestTest implements Runnable {

    private static final int CONNECT_TIMEOUT = 5000;
    private static final String SERVER_IP = "http://localhost:8080/";

    private LinkedList<RequestEntry> requestsQueue;

    private RestTemplate restTemplate;

    private AccessToken userToken;

    public RestTest() {
        this.requestsQueue = new LinkedList<>();
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        this.userToken = new AccessToken(ApiUtil.getBasicAuth("frontendClientId", "frontendClientSecret"));
        new Thread(this).start();
    }

    public void executeRequest(RequestEntry requestEntry) {
        synchronized (this.requestsQueue) {
            this.requestsQueue.add(requestEntry);
            this.requestsQueue.notify();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                RequestEntry currentRequest = null;
                synchronized (this.requestsQueue) {
                    if (this.requestsQueue.isEmpty()) {
                       this.requestsQueue.wait();
                    } else {
                        currentRequest = this.requestsQueue.poll();
                    }
                }
                // TODO handle request
                if (currentRequest != null)
                    currentRequest.getRequestResultInterface().onRequestResult(true, 200, new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setTimeout(RestTemplate restTemplate, int timeout) {
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        requestFactory.setReadTimeout(timeout);
        requestFactory.setConnectTimeout(timeout);
    }

    public void loginRequest(String username, String password) {
        Map<String, String> values = new HashMap<>();
        values.put("grant_type", "password");
        values.put("username", username);
        values.put("password", password);
        this.authRequest(values);
    }

    public void refreshRequest() {
        if (this.userToken.getUserToken() != null && this.userToken.getUserToken().getRefreshToken() != null) {
            Map<String, String> values = new HashMap<>();
            values.put("grant_type", "refresh_token");
            values.put("refresh_token", this.userToken.getUserToken().getRefreshToken());
            this.authRequest(values);
        } else {
            System.err.println("Refresh token not presented!");
        }
    }

    public void authRequest(Map<String, String> values) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.set("Authorization", ApiConstants.BASIC_AUTH + this.userToken.getBasicAuth());
        try {
            HttpEntity<String> request = new HttpEntity<>(ApiUtil.getStringChain(values), headers);
            ResponseEntity<TokenDTO> response = restTemplate.exchange(SERVER_IP + ApiConstants.AUTH_ENDPOINT, HttpMethod.POST, request, TokenDTO.class);
            this.userToken.setUserToken(response.getBody());
            response.getStatusCodeValue();
            System.out.println("Authorization success!");
        } catch (ResourceAccessException e) {
            System.err.println("Timeout!");
        } catch (HttpClientErrorException e) {
            System.err.println("HttpClientErrorException!");
            System.err.println(e.getRawStatusCode());
        } catch (RestClientException e) {
            System.err.println("RestClientException!");
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        RestTest restTest = new RestTest();

        restTest.executeRequest(new RequestEntry());

//        restTest.loginRequest("user@email.cm", "1234");
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        restTest.refreshRequest();

        System.out.println("End");
    }
}
