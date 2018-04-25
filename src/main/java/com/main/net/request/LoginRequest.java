package com.main.net.request;

import com.main.net.util.ApiUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends AuthRequest {

    public LoginRequest(String username, String password) {
        super();
        Map<String, String> values = new HashMap<>();
        values.put("grant_type", "password");
        values.put("username", username);
        values.put("password", password);
        this.setRequestBody(ApiUtil.getStringChain(values));
    }
}
