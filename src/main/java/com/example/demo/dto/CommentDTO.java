package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private String content;
    private Long commentator;
    private Integer type;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;

}
