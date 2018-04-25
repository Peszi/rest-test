package com.main.net.model;

public class TokenDTO {

    private String accessToken;
    private String refreshToken;
    private int expiresIn;

    public TokenDTO() {
        this.accessToken = "";
        this.refreshToken = "";
    }

    public void setAccess_token(String access_token) {
        this.accessToken = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refreshToken = refresh_token;
    }

    public void setExpires_in(int expires_in) {
        this.expiresIn = expires_in;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
