package com.main.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.net.model.AccessToken;
import com.main.net.model.UnauthorizedErrorDTO;
import com.main.net.request.BaseRequest;
import com.main.net.model.TokenDTO;
import com.main.net.request.RefreshRequest;
import com.main.net.util.ApiUtil;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.LinkedList;

public class ApiRequestFactory implements Runnable {

    private String serverIp;

    private LinkedList<BaseRequest> requestsQueue;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    private AccessToken userToken;

    public ApiRequestFactory(String serverIp, String clientId, String secret) {
        this.serverIp = serverIp;
        this.requestsQueue = new LinkedList<>();
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        this.objectMapper = new ObjectMapper();
        this.userToken = new AccessToken(ApiUtil.getBasicAuth(clientId, secret));
        new Thread(this).start();
    }

    public void executeRequest(BaseRequest baseRequest) {
        Logger.info("pushing request...");
        synchronized (this.requestsQueue) {
            this.requestsQueue.add(baseRequest);
            this.requestsQueue.notify();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                BaseRequest currentRequest = null;
                synchronized (this.requestsQueue) {
                    if (this.requestsQueue.isEmpty()) {
                        Logger.info("thread sleeping...");
                        this.requestsQueue.wait();
                        Logger.info("thread waking up!");
                    } else {
                        currentRequest = this.requestsQueue.poll();
                    }
                }
                if (currentRequest != null)
                    this.makeRequest(currentRequest);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private <T> void makeRequest(BaseRequest<T> baseRequest) {
        System.out.println("(making request " + baseRequest.getEndpoint() + " )");
        int statusCode = -1;
        boolean isTokenInvalid = false;
        try {
            baseRequest.setAccessToken(this.userToken);
            ResponseEntity<T> response = restTemplate.exchange(this.serverIp + baseRequest.getEndpoint(), baseRequest.getRequestMethod(),
                                                                baseRequest.getHttpEntity(), baseRequest.getResponseType(), baseRequest.getRequestParams());
            if (baseRequest.getResponseType().isAssignableFrom(TokenDTO.class)) {
                Logger.info("Authorization success!");
                this.userToken.setUserToken((TokenDTO) response.getBody());
            }
            if (baseRequest.hasRequestListener())
                baseRequest.getRequestListener().onRequestResult(true, response.getStatusCodeValue(), response.getBody());
            return;
        } catch (ResourceAccessException e) {
            System.err.println("Timeout!");
        } catch (HttpClientErrorException e) {
//            System.err.println(e.getRawStatusCode() + " " + e.getResponseBodyAsString());
            statusCode = e.getRawStatusCode();
            if (statusCode == HttpStatus.UNAUTHORIZED.value())
                isTokenInvalid = this.handleInvalidToken(e.getResponseBodyAsString());
        } catch (RestClientException e) {
            System.err.println("RestClientException!");
            System.err.println(e.getMessage());
        }
        if (isTokenInvalid && !baseRequest.getResponseType().isAssignableFrom(TokenDTO.class)) {
            System.err.println("!!!Invalid token!!!");
            this.executeRequest(new RefreshRequest());
            this.executeRequest(baseRequest);
        } else if (baseRequest.hasRequestListener()) {
            baseRequest.getRequestListener().onRequestResult(false, statusCode, null);
        }
    }

    private boolean handleInvalidToken(String message) {
        try {
            return (this.objectMapper.readValue(message, UnauthorizedErrorDTO.class).isExpired()) ? true : false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("not in use!")
    private void setTimeout(RestTemplate restTemplate, int timeout) {
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        requestFactory.setReadTimeout(timeout);
        requestFactory.setConnectTimeout(timeout);
    }
}
