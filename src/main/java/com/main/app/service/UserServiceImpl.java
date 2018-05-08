package com.main.app.service;

import com.main.api.model.TokenDTO;
import com.main.api.request.LoginRequest;
import com.main.api.request.LogoutRequest;
import com.main.app.RestClient;

public class UserServiceImpl implements UserService {

    private RestClient restClient;

    public UserServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void userLogin(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        request.setRequestListener(requestStatus -> {
            if (requestStatus.isStatusOK())
                System.out.println("logged in!");
            else
                System.err.println("[" + requestStatus.getErrorMessage() + "]");
        });
        this.restClient.sendRequest(request);
    }

    @Override
    public void userLogout() {
        LogoutRequest request = new LogoutRequest();
        request.setRequestListener(requestStatus -> {
            if (requestStatus.isStatusOK()) {
                System.out.println("logged out!");
                this.restClient.getAccessToken().setUserToken(new TokenDTO()); // remove saved token
            } else
                System.err.println("[" + requestStatus.getErrorMessage() + "]");
        });
        this.restClient.sendRequest(request);
    }
}
