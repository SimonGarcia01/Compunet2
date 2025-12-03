package org.example.finalproject.api.v1.dtos;

//Object only to send as response
public class MsgResp {

    private String message;

    public MsgResp(String message) {
        this.message = message;
    }

    public MsgResp() {
        //Default constructor
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
