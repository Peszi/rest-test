package com.main.app;

import com.main.CommandData;
import com.main.api.data.Param;
import com.main.api.model.TokenDTO;
import com.main.api.request.BaseRequest;
import com.main.api.request.DataRequest;
import com.main.api.request.LoginRequest;
import com.main.api.request.LogoutRequest;
import com.main.app.service.ClientService;
import com.main.app.service.ClientServiceImpl;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Console {

    private ClientService clientService;
    private Map<String, CommandsGroup> commandsMap;

    public Console() {
        this.clientService = new ClientServiceImpl();
        this.commandsMap = new HashMap<>();
        for (Method method : this.getClass().getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                final Command command = method.getAnnotation(Command.class);
                if (this.commandsMap.containsKey(command.group())) {
                    this.commandsMap.get(command.group()).addCommand(method.getName().toLowerCase(), new CommandData(method));
                } else {
                    CommandsGroup commandsGroup = new CommandsGroup();
                    commandsGroup.addCommand(method.getName().toLowerCase(), new CommandData(method));
                    this.commandsMap.put(command.group(), commandsGroup);
                }
            }
        }
        System.out.println("type 'login <username> <password>' to start..");
    }

    // Utilities

    public void executeCommand(String command, String... args) {
        boolean commandFound = false;
        for (Map.Entry<String, CommandsGroup> commandsGroup : this.commandsMap.entrySet()) {
            if (commandsGroup.getValue().containsKey(command)) {
                commandFound = true;
                CommandData commandData = commandsGroup.getValue().getCommand(command);
                if (!commandData.execute(this, args))
                    System.err.println(this.getInvalidArgsMessage(commandData));
            }
        }
        if (!commandFound)
            System.out.println("No command found!");
    }

    // Commands

    @Command(group = "utility", description = "print a list of all supported commands")
    public void help() {
        System.out.println("list of commands:");
        for (Map.Entry<String, CommandsGroup> commandsGroup : this.commandsMap.entrySet()) {
            System.out.println(" GROUP: " + commandsGroup.getKey().toUpperCase());
            for (CommandData commandData : commandsGroup.getValue().getCommandsMap().values())
                System.out.println("  -" + commandData.getDescription());
        }
    }

    @Command(group = "utility", arguments = "<token type>", description = "(string) print access or refresh token")
    public void token(String token) {
        this.clientService.printToken(token);
    }

    @Command(group = "utility", arguments = "<enable>", description = "(boolean) print debug data")
    public void debug(String enable) {
        this.clientService.enableDebug(enable);
    }

    // User

    @Command(group = "user", description = "<email>(string) <password>(string) logging with credentials")
    public void login(String email, String password) {
        this.clientService.getUserService().userLogin(email, password);
    }

    @Command(group = "user", description = "revoking access and refresh token")
    public void logout() {
      this.clientService.getUserService().userLogout();
    }

    // Room

    @Command(group = "room", description = "create new room")
    public void createRoom() {
        this.clientService.getRoomService().createRoom();
    }

    @Command(group = "room", description = "<room id>(long) joining room by room ID")
    public void joinRoom(String roomId) {
        this.clientService.getRoomService().joinRoom(roomId);
    }

    @Command(group = "room", description = "leaving current room")
    public void leaveRoom() {
        this.clientService.getRoomService().leaveRoom();
    }

    @Command(group = "room", description = "print current room details")
    public void room() {
        this.clientService.getRoomService().printRoom();
    }

    @Command(group = "room", description = "print all existing rooms")
    public void rooms() {
        this.clientService.getRoomService().printAllRooms();
    }

    @Command(group = "room", description = "<team id>(long) change team with team id")
    public void edtTeam(String teamId) {
        this.clientService.getRoomService().changeTeam(teamId);
    }

    // Host Room

//    @Command(group = "host", description = "<alias>(string) create a new team with name (room host)")
//    public void addTeam(String alias) {
//        this.requestInterface.sendRequest(new DataRequest<>("/api/room/host/team/", HttpMethod.POST, String.class));
//    }
//
//    @Command(group = "host", description = "<team id>(long) remove existing team (room host)")
//    public void rmvTeam(String teamId) {
//        this.requestInterface.sendRequest(new DataRequest<>("/api/room/host/team/" + teamId, HttpMethod.DELETE, String.class));
//    }
//
//    @Command(group = "host", description = "<gameMode>(int) change game mode (room host)")
//    public void edtMode(String gameMode) {
//        this.requestInterface.sendRequest(new DataRequest<>("/api/room/host/mode/" + gameMode, HttpMethod.POST, String.class));
//    }
//
//    @Command(group = "host", description = "<longitude>(long) <latitude>(long) <radius>(int) change game location (room host)")
//    public void edtLocation(String longitude, String latitude, String radius) {
//        DataRequest dataRequest = new DataRequest<>("/api/room/host/zone", HttpMethod.POST, String.class);
//        dataRequest.addParameter(new Param("zoneLongitude", longitude));
//        dataRequest.addParameter(new Param("zoneLatitude", latitude));
//        dataRequest.addParameter(new Param("zoneRadius", radius));
//        this.requestInterface.sendRequest(dataRequest);
//    }
//
//    @Command(group = "host", description = "<pointsLimit>(int) <timeLimit>(int) <zoneCapacity>(int) change zone control prefs (room host)")
//    public void edtZoneControl(String longitude, String latitude, String radius) {
//        DataRequest dataRequest = new DataRequest<>("/api/room/host/zone", HttpMethod.POST, String.class);
//        dataRequest.addParameter(new Param("pointsLimit", longitude));
//        dataRequest.addParameter(new Param("timeLimit", latitude));
//        dataRequest.addParameter(new Param("zoneCapacity", radius));
//        this.requestInterface.sendRequest(dataRequest);
//    }

    // Game

    @Command(group = "game", description = "update and get game data")
    public void update(String gameMode) {
        DataRequest dataRequest = new DataRequest<>("/api/room/game", HttpMethod.GET, String.class);
        dataRequest.setRequestListener(requestStatus -> {});
    }

    // Getters

    private String getInvalidArgsMessage(CommandData commandData) {
        return "invalid arguments - " + commandData.getArgumentsDescription();
    }
}
