package com.ahmety.movieinvio.Api;

import retrofit2.Response;

public class ApiError {
    private int statusCode;
    private String Message;

    public ApiError() {

    }
    public ApiError(Response response){
        this.statusCode = response.code();
        Message = response.message();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

}
