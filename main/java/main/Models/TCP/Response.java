package main.Models.TCP;

import main.Enums.ResponseStatus;

public class Response {
    private ResponseStatus responseStatus;
    private String responseMessage;

    public Response(ResponseStatus responseStatus, String responseMessage){
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
    }

    public Response(){

    }

    public String getResponseMessage() { return responseMessage;}

    public void setResponseMessage(String responseMessage){this.responseMessage = responseMessage;}

    public ResponseStatus qetResponseStatus(){return responseStatus;}

    public void setResponseStatus(ResponseStatus responseType){this.responseStatus = responseType;}
}
