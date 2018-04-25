package com.main.net.request;

import com.main.net.model.AccessToken;
import com.main.net.model.Param;
import com.main.net.model.TokenDTO;
import com.main.net.util.ApiConstants;
import com.main.net.util.ApiUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
