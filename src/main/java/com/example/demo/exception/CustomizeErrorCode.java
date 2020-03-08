package com.example.demo.exception;

public enum  CustomizeErrorCode  implements ICustomizeErrorCode{
    QUSTION_NOT_FOUND("你找的问题不存在，请重试。");

    private String message;
    @Override
    public String getMessage(){
        return this.message;
    }
    CustomizeErrorCode(String message){
        this.message = message;
    }
}
