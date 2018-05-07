package com.main.app;

import com.main.CommandData;
import com.main.api.request.DataRequest;
import com.main.api.request.LoginRequest;
import com.main.api.request.LogoutRequest;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Console {

    private RequestInterface requestInterface;
    private Map<String, CommandData> commandsMap;

    public Console(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
        this.commandsMap = new HashMap<>();
        for (Method method : this.getClass().getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                final Command command = method.getAnnotation(Command.class);
                this.commandsMap.put(command.name(), new CommandData(method));
            }
        }
    }

    // Commands

    @Command(name = "help", description = "print a list of all supported commands")
    public void printHelp() {
        System.out.println("list of commands:");
        for(CommandData command : this.commandsMap.values())
            System.out.println(">" + command.getDescription());
    }

    @Command(name = "token", description = "<token type> print access or refresh token")
    public void printToken(String token) {
        String tokenValue = this.requestInterface.getAccessToken().getAccessToken();
        if (token.equalsIgnoreCase("refresh"))
            tokenValue = this.requestInterface.getAccessToken().getRefreshToken();
        if (!tokenValue.isEmpty())
            System.out.println("token: " + tokenValue);
        else
            System.err.println("there is no token!");
    }

    @Command(name = "login", description = "<username> <password> logging with credentials")
    public void userLogin(String username, String password) {
        this.requestInterface.sendRequest(new LoginRequest(username, password));
    }

    @Command(name = "logout", description = "revoking access and refresh token")
    public void userLogout() {
        this.requestInterface.sendRequest(new LogoutRequest());
    }

    // Room

    @Command(name = "join", description = "<room id> joining room by room ID")
    public void joinRoom(String roomId) {
        try {
            this.requestInterface.sendRequest(new DataRequest<>("/api/rooms/" + Long.valueOf(roomId), HttpMethod.POST, String.class));
        } catch (NumberFormatException e) {
            System.err.println("incorrect argument, number expected!");
        }
    }

    @Command(name = "leave", description = "leaving current room")
    public void leaveRoom() {
        this.requestInterface.sendRequest(new DataRequest<>("/api/rooms", HttpMethod.DELETE, String.class));
    }

    @Command(name = "rooms", description = "print all existing rooms")
    public void getAllRooms() {
        this.requestInterface.sendRequest(new DataRequest<>("/api/rooms", HttpMethod.GET, String.class));
    }

    @Command(name = "room", description = "print current room details")
    public void getRoom() {
        this.requestInterface.sendRequest(new DataRequest<>("/api/room", HttpMethod.GET, String.class));
    }

    // Game

    @Command(name = "update", description = "update and get game data")
    public void updateGame() {
        this.requestInterface.sendRequest(new DataRequest<>("/api/room/game", HttpMethod.GET, String.class));
    }

    // Utilities

    public void executeCommand(String command, String... args) {
        if (this.commandsMap.containsKey(command)) {
            CommandData commandData = this.commandsMap.get(command);
            if (commandData.getArgsLen() < args.length)
                commandData.execute(this, this.getArguments(commandData.getArgsLen(), args));
            else
                System.err.println(this.getMissingArgsMessage((commandData.getArgsLen() + 1) - args.length));
        } else {
            System.out.println("No command found!");
        }
    }

    // Getters

    private String[] getArguments(int length, String... args) {
        String[] arguments = new String[length];
        for (int i = 0; i < length; i++)
            if ((i + 1) < args.length)
                arguments[i] = args[i + 1];
        return arguments;
    }

    private String getMissingArgsMessage(int argsCount) {
        if (argsCount > 1)
            return "missing " + argsCount + " arguments...";
        return "missing one argument...";
    }
}
