package com.main.app.service;

import com.main.api.request.DataRequest;
import com.main.app.RestClient;
import com.main.app.dto.basic.*;
import org.springframework.http.HttpMethod;

import java.util.List;

public class RoomServiceImpl implements RoomService {

    private RestClient restClient;

    public RoomServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void createRoom() {
        DataRequest<RoomDTO> dataRequest = new DataRequest<>("/api/room", HttpMethod.POST, RoomDTO.class);
        dataRequest.setRequestListener(requestStatus -> {
            if (requestStatus.isStatusOK())
                System.out.println("room created!");
            else
                System.err.println("[" + requestStatus.getErrorMessage() + "]");
        });
        this.restClient.sendRequest(dataRequest);
    }

    @Override
    public void joinRoom(String roomId) {
        DataRequest<RoomDTO> dataRequest = new DataRequest<>("/api/rooms/" + roomId, HttpMethod.POST, RoomDTO.class);
        dataRequest.setRequestListener(requestStatus -> {
            if (requestStatus.isStatusOK())
                System.out.println("joined to the room!");
            else
                System.err.println("[" + requestStatus.getErrorMessage() + "]");
        });
        this.restClient.sendRequest(dataRequest);
    }

    @Override
    public void leaveRoom() {
        DataRequest<String> dataRequest = new DataRequest<>("/api/rooms", HttpMethod.DELETE, String.class);
        dataRequest.setRequestListener(requestStatus -> {
            if (requestStatus.isStatusOK())
                System.out.println("left the room!");
            else
                System.err.println("[" + requestStatus.getErrorMessage() + "]");
        });
        this.restClient.sendRequest(dataRequest);
    }

    @Override
    public void printRoom() {
        DataRequest<FullRoomDTO> dataRequest = new DataRequest<>("/api/room", HttpMethod.GET, FullRoomDTO.class);
        dataRequest.setRequestListener(requestStatus -> {
            if (requestStatus.isStatusOK()) {
                FullRoomDTO room = requestStatus.getObject();
                System.out.println("======== ROOM ========");
                System.out.println("roomHost   : " + room.getHostName());
                System.out.println("isStarted  : " + room.isStarted());


                System.out.println("-------- TEAMS -------");
                for (TeamDTO team : room.getTeamsList()) {
                    System.out.println("TEAM " + team.getAlias());
                    for (BasicUserDTO user : team.getUsersList())
                        System.out.println(" - " + user.getName());
                }
                System.out.println("----------------------");
            } else
                System.err.println("[" + requestStatus.getErrorMessage() + "]");
        });
        this.restClient.sendRequest(dataRequest);
    }

    @Override
    public void printAllRooms() {
        DataRequest<RoomsListDTO> dataRequest = new DataRequest<>("/api/rooms", HttpMethod.GET, RoomsListDTO.class);
        dataRequest.setRequestListener(requestStatus -> {
            if (requestStatus.isStatusOK()) {
                List<RoomDTO> roomsList = requestStatus.getObject().getRoomsList();
                System.out.println("Has room " + requestStatus.getObject().isHasRoom());
                for (int i = 0; i < roomsList.size(); i++) {
                    final RoomDTO room = roomsList.get(i);
                    System.out.println("========= " + i + " ==========");
                    System.out.println("roomHost   : " + room.getHostName());
                    System.out.println("isStarted  : " + room.isStarted());
                    System.out.println("teamsCount : " + room.getTeamsCount());
                    System.out.println("----------------------");
                }
            } else
                System.err.println("[" + requestStatus.getErrorMessage() + "]");
        });
        this.restClient.sendRequest(dataRequest);
    }

    @Override
    public void changeTeam(String teamId) {
        DataRequest<String> dataRequest = new DataRequest<>("/api/room/team/" + teamId, HttpMethod.POST, String.class);
        dataRequest.setRequestListener(requestStatus -> {

        });
        this.restClient.sendRequest(dataRequest);
    }
}
