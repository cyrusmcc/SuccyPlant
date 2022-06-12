package com.cm.contentmanagementapp.payload.response;

public class MessageResponse {

    private String message;

    private Boolean success;

    public MessageResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
