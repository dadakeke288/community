package com.example.demo.enums;

public enum NotificationStatusEnum {
    UNREAD(0,""),READ(1,"");
    private int status;
    private String message;

    NotificationStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
