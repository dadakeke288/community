package com.example.demo.service;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public List<QuestionDTO> list() {
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for(Question question:questionMapper.list()){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            if(question.getCreator()==null) continue;
            questionDTO.setUser(userMapper.findById(question.getCreator()));
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        Integer offset = size*(page-1);
        for(Question question:questionMapper.listPage(offset,size)){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            if(question.getCreator()==null) continue;
            questionDTO.setUser(userMapper.findById(question.getCreator()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        Integer totalCount = questionMapper.count();
        paginationDTO.setPageArr(totalCount,page,size);


        return paginationDTO;
    }
    public PaginationDTO list(Integer userId,Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        Integer offset = size*(page-1);
        for(Question question:questionMapper.listUser(userId,offset,size)){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            if(question.getCreator()==null) continue;
            questionDTO.setUser(userMapper.findById(question.getCreator()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        Integer totalCount = questionMapper.countUser(userId);
        paginationDTO.setPageArr(totalCount,page,size);


        return paginationDTO;
    }
}
