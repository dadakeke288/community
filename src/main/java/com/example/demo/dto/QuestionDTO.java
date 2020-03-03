package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

//Quetion 的传输模型
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String content;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
