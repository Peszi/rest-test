package com.main;

import com.main.api.request.LogoutRequest;
import com.main.api.service.ApiRequestFactoryImpl;
import com.main.api.service.ApiRequestFactory;
import com.main.api.listener.AuthErrorListener;
import com.main.api.listener.RequestResultListener;
import com.main.api.request.DataRequest;
import com.main.api.request.LoginRequest;
import org.springframework.http.HttpMethod;

public class RestTest<T> implements AuthErrorListener, RequestResultListener<T> {

    private static final String SERVER_IP = "http://localhost:8080/";

    private static final String CLIENT_ID = "mobileClientId";
    private static final String CLIENT_SECRET = "mobileSecret";
    private static final int CLIENT_TIMEOUT = 5000;

    private ApiRequestFactory requestFactory;

    public RestTest() {
        this.requestFactory = new ApiRequestFactoryImpl(SERVER_IP, CLIENT_ID, CLIENT_SECRET);
        this.requestFactory.setAuthErrorListener(this);

        LoginRequest loginRequest = new LoginRequest("user2@email.com", "1234");
        loginRequest.setRequestListener(this);
        this.requestFactory.executeRequest(loginRequest);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataRequest<String> dataRequest = new DataRequest<>("/api/user", HttpMethod.GET, String.class);
        dataRequest.setRequestListener(this);
        this.requestFactory.executeRequest(dataRequest);

        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setRequestListener(this);
        this.requestFactory.executeRequest(logoutRequest);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.requestFactory.executeRequest(dataRequest);
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

    @Override
    public void onAuthError(String message) {
        System.out.println("onAuthError " + message);
    }

    public static void main(String[] args) {
        new RestTest();
    }
}
