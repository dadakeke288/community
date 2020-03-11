package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
    private Long notifier;
    private String notifierName;
}
