package com.main.data;

import com.main.net.model.Param;

public class Credentials {

    private String username;
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Param getUsernameParam() {
        return new Param("username", this.username);
    }

    public Param getPasswordParam() {
        return new Param("password", this.password);
    }
}
