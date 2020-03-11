package com.example.demo.exception;

public enum  CustomizeErrorCode  implements ICustomizeErrorCode{
    QUSTION_NOT_FOUND(2001,"你找的问题不存在，请重试。"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中对任何帖子或回复"),
    NOT_LOGIN(2003,"未登录不能进行评论，请先登录。"),
    SYSTEM_ERROR(2004,"服务端异常，请稍后重试。"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你回复的评论不存在"),
    CONTENT_IS_EMPTY(2007,"你输入的内容为空"),
    READ_NOTIFICATION_FAIL(2008,"读取信息状态异常"),
    NOTIFICATION_NOT_FOUND(2009,"通知消息不存在");

    private String message;
    private Integer code;
    @Override
    public String getMessage(){
        return this.message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
