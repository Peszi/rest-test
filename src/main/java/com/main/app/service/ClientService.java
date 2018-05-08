package com.main.app.service;

public interface ClientService {
    void printToken(String token);
    void enableDebug(String enable);

    UserService getUserService();
    RoomService getRoomService();
}
