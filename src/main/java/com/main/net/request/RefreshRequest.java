package com.main.net.request;

import com.main.net.model.AccessToken;
import com.main.net.util.ApiUtil;

import java.util.HashMap;
import java.util.Map;

public class RefreshRequest extends AuthRequest {

    public RefreshRequest() {
        super();
    }

    @Override
    public void setAccessToken(AccessToken accessToken) {
        super.setAccessToken(accessToken);
        Map<String, String> values = new HashMap<>();
        values.put("grant_type", "refresh_token");
        values.put("refresh_token", accessToken.getUserToken().getRefreshToken());
        this.setRequestBody(ApiUtil.getStringChain(values));
    }
}
