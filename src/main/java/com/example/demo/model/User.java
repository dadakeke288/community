package com.example.demo.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;//账户创建时间
    private Long gmtModified;//账户最近修改时间
    private String bio;
    private String avatarUrl;//头像地址
}
