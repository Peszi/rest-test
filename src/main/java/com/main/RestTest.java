package com.main;

import com.main.api.Commands;
import com.main.api.request.BaseRequest;
import com.main.api.request.LoginRequest;
import com.main.api.request.LogoutRequest;
import com.main.api.service.ApiRequestFactoryImpl;
import com.main.api.service.ApiRequestFactory;
import com.main.api.listener.AuthErrorListener;
import com.main.api.listener.RequestResultListener;
import com.main.api.request.DataRequest;
import org.springframework.http.HttpMethod;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Scanner;

public class RestTest<T> implements AuthErrorListener, RequestResultListener<T> {

    private static final String SERVER_IP = "http://localhost:8080/";

    private static final String CLIENT_ID = "mobileClientId";
    private static final String CLIENT_SECRET = "mobileSecret";
    private static final int CLIENT_TIMEOUT = 5000;

    private ApiRequestFactory requestFactory;

    private long lastTime;
    private String[] lineArgs;
    private int lineArgsCount;

    public RestTest() {
        this.lastTime = System.currentTimeMillis();
        this.requestFactory = new ApiRequestFactoryImpl(SERVER_IP, CLIENT_ID, CLIENT_SECRET);
        this.requestFactory.setAuthErrorListener(this);

        String line = "";
        Scanner scanner = new Scanner(System.in);
        while ((line = scanner.nextLine()) != null) {
            if (!this.checkAndReadArgs(line)) { continue; }
            switch (this.lineArgs[0].toLowerCase()) {
                case Commands.LOGIN: this.login(); break;
                case Commands.LOGOUT: this.logout(); break;
                case Commands.TOKEN: this.printToken(); break;
                case Commands.UPDATE: this.updateGame(); break;
                case Commands.ROOMS: this.getAllRooms(); break;
                case Commands.JOIN: this.joinRoom(); break;
                case Commands.LEAVE: this.leaveRoom(); break;
                case Commands.ROOM: this.getRoom(); break;
                case "help" : this.help(); break;
                default:
                    System.out.println("No command found!"); break;
            }
        }
    }

    private void login() {
        if (!this.checkArgsLength(2))
            return;
        LoginRequest loginRequest = new LoginRequest(this.lineArgs[1] + "@email.com", "123456");
        loginRequest.setRequestListener(this);
        this.sendRequest(loginRequest);
    }

    private void logout() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setRequestListener(this);
        this.sendRequest(logoutRequest);
    }

    private void printToken() {
        final String accessToken = this.requestFactory.getAccessToken().getUserToken().getAccessToken();
        if (!accessToken.isEmpty())
            System.out.println("access token: " + accessToken);
        else
            System.err.println("no token!");
    }

    // Room

    private void joinRoom() {
        if (!this.checkArgsLength(2))
            return;
        DataRequest dataRequest = new DataRequest<>("/api/rooms/" + Long.valueOf(this.lineArgs[1]), HttpMethod.POST, String.class);
        dataRequest.setRequestListener(this);
        this.sendRequest(dataRequest);
    }

    private void leaveRoom() {
        DataRequest dataRequest = new DataRequest<>("/api/rooms", HttpMethod.DELETE, String.class);
        dataRequest.setRequestListener(this);
        this.sendRequest(dataRequest);
    }

    private void getAllRooms() {
        DataRequest dataRequest = new DataRequest<>("/api/rooms", HttpMethod.GET, String.class);
        dataRequest.setRequestListener(this);
        this.sendRequest(dataRequest);
    }

    private void getRoom() {
        DataRequest dataRequest = new DataRequest<>("/api/room", HttpMethod.GET, String.class);
        dataRequest.setRequestListener(this);
        this.sendRequest(dataRequest);
    }

    private void updateGame() {
        DataRequest dataRequest = new DataRequest<>("/api/room/game", HttpMethod.GET, String.class);
        dataRequest.setRequestListener(this);
        this.sendRequest(dataRequest);
    }

    private void help() {
        System.out.println("list of commands:");
        final Commands commands = new Commands();
        for(Field field : Commands.class.getFields()) {
            try {
                System.out.println(" " + field.get(commands));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkArgsLength(int expectedLength) {
        if (this.lineArgsCount >= expectedLength)
            return true;
        System.err.println("Incorrect number of params! expected:" + expectedLength);
        return false;
    }

    private boolean checkAndReadArgs(String line) {
        this.lineArgs = line.split(" ");
        this.lineArgsCount = this.lineArgs.length;
        if (this.lineArgsCount > 0)
            return true;
        System.err.println("Incorrect argument!");
        return false;
    }

    private void sendRequest(BaseRequest request) {
        this.lastTime = System.currentTimeMillis();
        this.requestFactory.executeRequest(request);
    }

    @Override
    public void onRequestResult(boolean status, int statusCode, T object) {
        final long elapsedTime = (System.currentTimeMillis() - this.lastTime);
        String message = "";
        if (object instanceof String)
            message = "[" + object + "]";
        PrintStream out = System.out;
        if (!status)
            out = System.err;
        out.println("status: " + statusCode + " time: " + elapsedTime + " " + message);
    }

    @Override
    public void onAuthError(String message) {
        System.err.println("authorization error... " + message);
    }

    public static void main(String[] args) {
        new RestTest();
    }
}
