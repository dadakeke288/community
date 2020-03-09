package com.example.demo.controller;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model,
                           @RequestParam(name = "page",defaultValue = "1") Integer page,
                           @RequestParam(name = "size",defaultValue = "5") Integer size){
        QuestionDTO questionDTO = questionService.getById(id);
        //增加浏览量
        questionService.incView(id);
        if (questionDTO!=null){
            model.addAttribute("question",questionDTO);
        }
        List<CommentDTO> commentDTOS = commentService.listByQustionId(id,page,size);
        if (commentDTOS!=null){
            model.addAttribute("comments",commentDTOS);
        }
        return "question";
    }
}
