package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id" ) Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        if (question==null) return "publish";
        model.addAttribute("title",question.getTitle());
        model.addAttribute("content",question.getContent());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("questionId",id);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("tag") String tag,
                            @RequestParam(value = "questionId",required = false) Long questionId,
                            HttpServletRequest request,
                            Model model){
        // error 判断
        if (title == null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (content == null || content==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if (tag == null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        //get user
        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
        }

        //
        model.addAttribute("title",title);
        model.addAttribute("content",content);
        model.addAttribute("tag",tag);
        //question
        Question question = new Question();
        question.setId(questionId);
        question.setTitle(title);
        question.setContent(content);
        question.setTag(tag);
        question.setCreator(user.getId());

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
