package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;
@Data
//Quetion 的传输模型
public class QuestionDTO {
    private Long id;
    private String title;
    private String content;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
