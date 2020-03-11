package com.example.demo.controller;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.model.Notification;
import com.example.demo.model.User;
import com.example.demo.service.NotificationService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action" ) String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size) {

        User user=(User) request.getSession().getAttribute("user");
        if (user==null) return "redirect:/";

        if (action.equals("mypub")){
            model.addAttribute("section","mypub");
            model.addAttribute("sectionName","我的发布");
            //查数据，展示
            PaginationDTO paginationDto = questionService.list(user.getId(),page,size);
            if (paginationDto!=null)
                model.addAttribute("pagination",paginationDto);
        }else if(action.equals("replies")){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","动态消息");
            //查数据，展示
            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("section","replies");
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("unreadCount",unreadCount);
        }


        return "profile";
    }
}
