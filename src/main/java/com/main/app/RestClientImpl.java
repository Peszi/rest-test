package com.main.app;

import com.main.api.data.AccessToken;
import com.main.api.data.RequestStatus;
import com.main.api.listener.AuthorizationListener;
import com.main.api.listener.RequestResultListener;
import com.main.api.request.BaseRequest;
import com.main.api.service.ApiRequestFactory;
import com.main.api.service.ApiRequestFactoryImpl;

import java.io.PrintStream;

import static com.main.app.ClientConstants.CLIENT_ID;
import static com.main.app.ClientConstants.CLIENT_SECRET;
import static com.main.app.ClientConstants.SERVER_IP;

public class RestClientImpl implements RestClient, AuthorizationListener, RequestResultListener {

    private ApiRequestFactory requestFactory;
    private long requestStartTime;

    public RestClientImpl() {
        this.requestFactory = new ApiRequestFactoryImpl(SERVER_IP, CLIENT_ID, CLIENT_SECRET);
        this.requestFactory.setAuthorizationListener(this);
    }

    // Request debugging

    @Override
    public void enableDebug(boolean enable) {
        this.requestFactory.enableDebug(enable);
    }

    // Send requests

    @Override
    public void sendRequest(BaseRequest request) {
        this.requestFactory.executeRequest(request);
    }

    @Override
    public void sendRequestForResult(BaseRequest request) {
        this.requestStartTime = System.currentTimeMillis();
        request.setRequestListener(this);
        this.requestFactory.executeRequest(request);
    }

    // Request callbacks

    @Override
    public void onRequestResult(RequestStatus requestStatus) {
        final long requestTime = (System.currentTimeMillis() - this.requestStartTime);
        String message = "";
        if (requestStatus.getObject() instanceof String)
            message = "[" + requestStatus.getObject() + "]";
        PrintStream out = System.out;
        if (!requestStatus.isStatusOK())
            out = System.err;
        out.println("status: " + requestStatus.getStatusCode() + " time: " + requestTime + " " + message + " {" + requestStatus.getErrorMessage() + "}");
    }

    @Override
    public void onClientLoggedOut(String message) {
        System.err.println("(log out action)");
    }

    // Getters

    @Override
    public AccessToken getAccessToken() {
        return this.requestFactory.getAccessToken();
    }
}
