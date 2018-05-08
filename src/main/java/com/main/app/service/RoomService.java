package com.main.app.service;

public interface RoomService {
    void createRoom();
    void joinRoom(String roomId);
    void leaveRoom();
    void printRoom();
    void printAllRooms();
    void changeTeam(String teamId);
}
