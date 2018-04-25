package com.main;

import com.main.net.ApiRequestFactory;
import com.main.net.Logger;
import com.main.net.callback.RequestResultInterface;
import com.main.net.request.BaseRequest;
import com.main.net.model.TokenDTO;
import com.main.net.request.DataRequest;
import com.main.net.request.LoginRequest;
import com.main.net.request.RefreshRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTest<T> implements RequestResultInterface<T> {

    private static final String SERVER_IP = "http://localhost:8080/";

    private static final String CLIENT_ID = "frontendClientId";
    private static final String CLIENT_SECRET = "frontendClientSecret";
    private static final int CLIENT_TIMEOUT = 5000;

    private ApiRequestFactory apiRequestFactory;

    public RestTest() {
        this.apiRequestFactory = new ApiRequestFactory(SERVER_IP, CLIENT_ID, CLIENT_SECRET);

        Logger.info("Init");

        LoginRequest loginRequest = new LoginRequest("user32@email.com", "1234");
        loginRequest.setRequestListener(this);
        this.apiRequestFactory.executeRequest(loginRequest);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataRequest<String> dataRequest = new DataRequest<>("/user", HttpMethod.GET, String.class);
        dataRequest.setRequestListener(this);
        this.apiRequestFactory.executeRequest(dataRequest);
    }

    @Override
    public void onRequestResult(boolean status, int statusCode, T object) {
        if (status)
            System.out.println("Result " + status + " Code " + statusCode);
        else
            System.err.println("Result " + status + " Code " + statusCode);

        if (object instanceof String)
            System.out.println("> " + object + " <");
    }

    public static void main(String[] args) {
        new RestTest();
    }
}
