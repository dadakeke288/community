package com.example.demo.enums;

import org.h2.value.ExtTypeInfoEnum;

public enum NotificationEnum {
    REPLY_QUESTION(1,"回复主贴"),
    REPLY_COMMENT(2,"回复评论"),
    LIKE_QUESTION(3,"点赞主贴"),
    LIKE_COMMENT(4,"点赞评论");

    private int type;
    private String name;
    NotificationEnum(int type, String name){
        this.name = name;
        this.type = type;
    }
    public static String nameOf(int type){
        for (NotificationEnum notificationEnum:NotificationEnum.values()){
            if (notificationEnum.getType() == type){
                return notificationEnum.getName();
            }
        }
        return "";
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
