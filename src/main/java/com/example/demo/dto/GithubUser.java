package com.example.demo.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private long id;
    private String bio;//描述
    private String avatar_url;//头像地址
}
