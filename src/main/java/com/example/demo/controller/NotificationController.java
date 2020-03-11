package com.example.demo.controller;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.enums.NotificationEnum;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notificate(HttpServletRequest request,
                             @PathVariable(name = "id") Long id){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null) return "redirect:/";

        NotificationDTO notificationDTO = notificationService.read(id,user);

        if (notificationDTO.getType()== NotificationEnum.REPLY_QUESTION.getType() ||
                notificationDTO.getType()== NotificationEnum.REPLY_COMMENT.getType()) {
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else{

        }
        return "redirect:profile/replies";
    }
}
