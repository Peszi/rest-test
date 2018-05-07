package com.main;

import com.main.api.data.AccessToken;
import com.main.api.model.TokenDTO;
import com.main.app.Command;
import com.main.app.Commands;
import com.main.api.request.BaseRequest;
import com.main.api.request.LoginRequest;
import com.main.api.request.LogoutRequest;
import com.main.api.service.ApiRequestFactoryImpl;
import com.main.api.service.ApiRequestFactory;
import com.main.api.listener.AuthErrorListener;
import com.main.api.listener.RequestResultListener;
import com.main.api.request.DataRequest;
import com.main.app.Console;
import com.main.app.RequestInterface;
import org.springframework.http.HttpMethod;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RestTest<T> implements RequestInterface, AuthErrorListener, RequestResultListener<T> {

    private static final String SERVER_IP = "http://localhost:8080/";

    private static final String CLIENT_ID = "mobileClientId";
    private static final String CLIENT_SECRET = "mobileSecret";
    private static final int CLIENT_TIMEOUT = 5000;

    private Console console;
    private ApiRequestFactory requestFactory;

    private long lastTime;
    private String[] lineArgs;
    private int lineArgsCount;

    public RestTest() {
        this.console = new Console(this);
        this.requestFactory = new ApiRequestFactoryImpl(SERVER_IP, CLIENT_ID, CLIENT_SECRET);
        this.requestFactory.setAuthErrorListener(this);

        String line;
        Scanner scanner = new Scanner(System.in);
        while ((line = scanner.nextLine()) != null) {
            if (!this.checkAndReadArgs(line))
                continue;
            this.console.executeCommand(this.lineArgs[0].toLowerCase(), this.lineArgs);
        }
    }

    @Override
    public void sendRequest(BaseRequest request) {
        this.lastTime = System.currentTimeMillis();
        request.setRequestListener(this);
        this.requestFactory.executeRequest(request);
    }

    @Override
    public TokenDTO getAccessToken() {
        return this.requestFactory.getAccessToken().getUserToken();
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
