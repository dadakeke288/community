package com.example.demo.controller;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        User user=null;
        if (cookies!=null && cookies.length!=0){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user!=null) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if (user==null) return "redirect:/";
        if (action.equals("mypub")){
            model.addAttribute("section","mypub");
            model.addAttribute("sectionName","我的发布");
        }else if(action.equals("replies")){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","动态消息");
        }

        //查数据，展示
        PaginationDTO paginationDto = questionService.list(user.getId(),page,size);
        if (paginationDto!=null)
            model.addAttribute("pagination",paginationDto);
        return "profile";
    }
}
