package com.main.api.request;

import com.main.api.data.AccessToken;
import com.main.api.model.TokenDTO;
import com.main.api.util.ApiConstants;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class AuthRequest extends BaseRequest<TokenDTO> {

    public AuthRequest() {
        super(ApiConstants.AUTH_ENDPOINT, HttpMethod.POST, TokenDTO.class);
        this.getRequestHeaders().set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    @Override
    public void setAccessToken(AccessToken accessToken) {
        this.getRequestHeaders().set("Authorization", ApiConstants.BASIC_AUTH + accessToken.getBasicAuth());
    }
}
