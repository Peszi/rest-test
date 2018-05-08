package com.main.app.service;

import com.main.api.model.TokenDTO;
import com.main.api.request.BaseRequest;
import com.main.api.request.LoginRequest;
import com.main.api.request.LogoutRequest;
import com.main.app.RestClient;
import com.main.app.RestClientImpl;

public class ClientServiceImpl implements ClientService {

    private RestClient restClient;
    private UserService userService;
    private RoomService roomService;

    public ClientServiceImpl() {
        this.restClient = new RestClientImpl();
        this.userService = new UserServiceImpl(this.restClient);
        this.roomService = new RoomServiceImpl(this.restClient);
    }

    // Getters

    @Override
    public UserService getUserService() {
        return this.userService;
    }

    @Override
    public RoomService getRoomService() {
        return this.roomService;
    }

    // Utility

    @Override
    public void printToken(String token) {
        String tokenValue = this.restClient.getAccessToken().getUserToken().getAccessToken();
        if (token.equalsIgnoreCase("refresh"))
            tokenValue = this.restClient.getAccessToken().getUserToken().getRefreshToken();
        if (!tokenValue.isEmpty())
            System.out.println("token: " + tokenValue);
        else
            System.err.println("there is no token!");
    }

    @Override
    public void enableDebug(String enable) {
        if (enable.equalsIgnoreCase("true")) {
            this.restClient.enableDebug(true);
            System.out.println("debug enabled!");
            return;
        }
        this.restClient.enableDebug(false);
        System.out.println("debug disabled!");
    }
}
