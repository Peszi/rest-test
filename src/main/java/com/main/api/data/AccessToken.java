package com.main.api.data;

import com.main.api.model.TokenDTO;

public class AccessToken {

    private String basicAuth;
    private TokenDTO userToken;

    public AccessToken(String basicAuth) {
        this.basicAuth = basicAuth;
        this.userToken = new TokenDTO();
    }

    public void setBasicAuth(String basicAuth) {
        this.basicAuth = basicAuth;
    }

    public void setUserToken(TokenDTO userToken) {
        this.userToken = userToken;
    }

    public String getBasicAuth() {
        return basicAuth;
    }

    public TokenDTO getUserToken() {
        return userToken;
    }
}
