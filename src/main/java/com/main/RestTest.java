package com.main;

import com.main.data.Credentials;
import com.main.data.UserData;
import com.main.net.JsonRequestFactory;
import com.main.net.Param;
import com.main.net.ResponseObject;
import org.springframework.http.HttpMethod;

public class RestTest {

    private static final String SERVER_IP = "http://localhost:8080/";

    public static void main(String[] args) {

        JsonRequestFactory jsonRequestFactory = new JsonRequestFactory(SERVER_IP);

        final String username = "John";
        final String password = "1234";

        System.out.println("REGISTER ");

        ResponseObject<String> stringResponseObject = jsonRequestFactory.makeRequest("user/register", HttpMethod.POST, new Param("username", username), new Param("password", password));
        if (!stringResponseObject.isError()) {
            System.out.println("Success " + stringResponseObject.getObject().getBody());
        } else {
//            System.err.println("Error " + ((ServerErrorMessage) responseEntity.getBody()).getMessage());
            System.err.println("Error " + stringResponseObject.getError().getMessage());
        }

        String api = "";

        System.out.println("LOGIN ");
        ResponseObject<UserData> userDataResponseObject = jsonRequestFactory.makeRequest("user/login", HttpMethod.POST, UserData.class,
                new Param("username", username), new Param("password", password));
        if (!userDataResponseObject.isError()) {
            System.out.println("Success " + userDataResponseObject.getObject().getBody().toString());
            api = userDataResponseObject.getObject().getBody().getApiKey();
        } else {
//            System.err.println("Error " + ((ServerErrorMessage) responseEntity.getBody()).getMessage());
            System.err.println("Error " + userDataResponseObject.getError().getMessage());
        }

        System.out.println("LOGIN HERE");
        userDataResponseObject = jsonRequestFactory.makeRequest("user/login", HttpMethod.POST, UserData.class, new Param("username", username), new Param("password", "asd"));
        if (!userDataResponseObject.isError()) {
            System.out.println("Success " + userDataResponseObject.getObject().getBody().toString());
        } else {
//            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
            System.err.println("Error " + userDataResponseObject.getError().getMessage());
        }

        System.out.println("LOGIN ");
        userDataResponseObject = jsonRequestFactory.makeRequest("user/login", HttpMethod.POST, UserData.class, new Param("username", "Ba"), new Param("password", password));
        if (!userDataResponseObject.isError()) {
            System.out.println("Success " + userDataResponseObject.getObject().getBody().toString());
        } else {
//            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
            System.err.println("Error " + userDataResponseObject.getError().getMessage());
        }

//        System.out.println("DELETE ");
//        stringResponseObject = jsonRequestFactory.makeRequest("user/delete", HttpMethod.POST, new Param("apiKey", api));
//        if (!stringResponseObject.isError()) {
//            System.out.println("Success " + stringResponseObject.getObject().getBody());
//        } else {
////            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
//            System.err.println("Error " + stringResponseObject.getError().getMessage());
//        }
//
        System.out.println("DELETE ");
        stringResponseObject = jsonRequestFactory.makeRequest("user/delete", HttpMethod.POST, new Param("apiKey", "dsasdasdasd"));
        if (!stringResponseObject.isError()) {
            System.out.println("Success " + stringResponseObject.getObject().getBody());
        } else {
//            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
            System.err.println("Error " + stringResponseObject.getError().getMessage());
        }

        System.out.println("LOGIN HERE");
        userDataResponseObject = jsonRequestFactory.makeRequest("user/login", HttpMethod.POST, UserData.class, new Param("username", username), new Param("password", "asdsfdsfsd"));
        if (!userDataResponseObject.isError()) {
            System.out.println("Success " + userDataResponseObject.getObject().getBody().toString());
        } else {
//            System.err.println("Error " + jsonRequestFactory.getServerErrorResponse().getMessage());
            System.err.println("Error " + userDataResponseObject.getError().getMessage());
        }

    }
}
