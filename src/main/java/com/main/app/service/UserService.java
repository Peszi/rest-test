package com.main.app.service;

public interface UserService {
    void userLogin(String email, String password);
    void userLogout();
}
