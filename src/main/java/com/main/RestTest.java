package com.main;

import com.main.data.Credentials;
import com.main.data.UserData;
import com.main.net.JsonRequestFactory;
import com.main.net.Param;
import com.main.net.ResponseObject;
import org.springframework.http.HttpMethod;

public class RestTest {

    private static final String SERVER_IP = "http://localhost:8080/";

    private JsonRequestFactory jsonRequestFactory = new JsonRequestFactory(SERVER_IP);

    public RestTest() {
        final Credentials credentials = new Credentials("User", null);
        this.registerUser(credentials);
        String key = this.loginUser(credentials);
        this.createRoom(key);
        this.joinRoom(key, 1L);
    }

    private boolean registerUser(Credentials credentials) {
        System.out.println("registering... ");
        ResponseObject<String> stringResponseObject = jsonRequestFactory.makeRequest("user", HttpMethod.POST, credentials.getUsernameParam(), credentials.getPasswordParam());
        if (!stringResponseObject.isError()) {
            System.out.println("Success " + stringResponseObject.getResponse().getBody());
            return true;
        }
        stringResponseObject.getError().ifPresent(serverErrorMessage -> System.err.println("ERR " + serverErrorMessage.getMessage()));
        return false;
    }

    private String loginUser(Credentials credentials) {
        System.out.println("logging... ");
        ResponseObject<UserData> stringResponseObject = jsonRequestFactory.makeRequest("user", HttpMethod.GET, UserData.class, credentials.getUsernameParam(), credentials.getPasswordParam());
        if (!stringResponseObject.isError()) {
            System.out.println("Success " + stringResponseObject.getResponse().getBody());
            return stringResponseObject.getResponse().getBody().getApiKey();
        }
        stringResponseObject.getError().ifPresent(serverErrorMessage -> System.err.println("ERR " + serverErrorMessage.getMessage()));
        return null;
    }

    private boolean createRoom(String key) {
        System.out.println("room... ");
        ResponseObject<String> stringResponseObject = jsonRequestFactory.makeRequest("room", HttpMethod.POST, new Param("key", key));
        if (!stringResponseObject.isError()) {
            System.out.println("Success " + stringResponseObject.getResponse().getBody());
            return true;
        }
        stringResponseObject.getError().ifPresent(serverErrorMessage -> System.err.println("ERR " + serverErrorMessage.getMessage()));
        return false;
    }

    private boolean joinRoom(String key, long roomId) {
        System.out.println("join... ");
        ResponseObject<String> stringResponseObject = jsonRequestFactory.makeRequest("room", HttpMethod.POST, new Param("key", key), new Param("roomId", roomId));
        if (!stringResponseObject.isError()) {
            System.out.println("Success " + stringResponseObject.getResponse().getBody());
            return true;
        }
        stringResponseObject.getError().ifPresent(serverErrorMessage -> System.err.println("ERR " + serverErrorMessage.getMessage()));
        return false;
    }

    private void test() {
        final String username = "John";
        final String password = "1234";



//        String api = "";
//
//        System.out.println("LOGIN ");
//        ResponseObject<UserData> userDataResponseObject = jsonRequestFactory.makeRequest("user/login", HttpMethod.GET, UserData.class,
//                new Param("username", username), new Param("password", password));
//        if (!userDataResponseObject.isError()) {
//            System.out.println("Success " + userDataResponseObject.getResponse().getBody().toString());
//            api = userDataResponseObject.getResponse().getBody().getApiKey();
//        } else {
////            System.err.println("Error " + ((ServerErrorMessage) responseEntity.getBody()).getMessage());
//            System.err.println("Error " + userDataResponseObject.getError().getMessage());
//        }
//
//        System.out.println("LOGIN HERE");
//        userDataResponseObject = jsonRequestFactory.makeRequest("user/login", HttpMethod.GET, UserData.class, new Param("username", username), new Param("password", "asd"));
//        if (!userDataResponseObject.isError()) {
//            System.out.println("Success " + userDataResponseObject.getResponse().getBody().toString());
//        } else {
////            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
//            System.err.println("Error " + userDataResponseObject.getError().getMessage());
//        }
//
//        System.out.println("LOGIN ");
//        userDataResponseObject = jsonRequestFactory.makeRequest("user/login", HttpMethod.GET, UserData.class, new Param("username", "Ba"), new Param("password", password));
//        if (!userDataResponseObject.isError()) {
//            System.out.println("Success " + userDataResponseObject.getResponse().getBody().toString());
//        } else {
////            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
//            System.err.println("Error " + userDataResponseObject.getError().getMessage());
//        }
//
////        System.out.println("DELETE ");
////        stringResponseObject = jsonRequestFactory.makeRequest("user/delete", HttpMethod.POST, new Param("apiKey", api));
////        if (!stringResponseObject.isError()) {
////            System.out.println("Success " + stringResponseObject.getResponse().getBody());
////        } else {
//////            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
////            System.err.println("Error " + stringResponseObject.getError().getMessage());
////        }
////
//        System.out.println("DELETE ");
//        stringResponseObject = jsonRequestFactory.makeRequest("user/delete", HttpMethod.DELETE, new Param("apiKey", api));
//        if (!stringResponseObject.isError()) {
//            System.out.println("Success " + stringResponseObject.getObject().getBody());
//        } else {
////            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
//            System.err.println("Error " + stringResponseObject.getError().getMessage());
//        }
//
//        System.out.println("LOGIN HERE");
//        userDataResponseObject = jsonRequestFactory.makeRequest("user/login", HttpMethod.GET, UserData.class, new Param("username", username), new Param("password", "asdsfdsfsd"));
//        if (!userDataResponseObject.isError()) {
//            System.out.println("Success " + userDataResponseObject.getResponse().getBody().toString());
//        } else {
////            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
//            System.err.println("Error " + userDataResponseObject.getError().getMessage());
//        }
    }

    public static void main(String[] args) {
        new RestTest();
    }
}
