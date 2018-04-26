package com.main.api;

import com.main.error.ServerErrorMessage;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Deprecated
public class ResponseObject<T> {

    private ResponseEntity<T> responseObject;
    private ServerErrorMessage responseError;

    public void setResponseObject(ResponseEntity<T> responseObject) {
        this.responseObject = responseObject;
    }

    public void setResponseError(ServerErrorMessage responseError) {
        this.responseError = responseError;
    }

    public ResponseEntity<T> getResponse() {
        return responseObject;
    }

    public Optional<ServerErrorMessage> getError() {
        return Optional.of(responseError);
    }

    public boolean isError() {
//        System.out.println("is ERR " +  (this.responseError != null) + " status " + HttpStatus.valueOf(this.responseError.getStatus()).isError());
        if (this.responseObject != null)
            return false;
        if (this.responseError != null)
            return true; //(HttpStatus.valueOf(this.responseError.getStatus()).isError()) ? true : false;
        System.out.println("Weird behaivor in Response Object");
        return true;
    }
}
