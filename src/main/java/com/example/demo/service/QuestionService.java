package com.example.demo.service;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.QuestionExtMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null) throw new CustomizeException(CustomizeErrorCode.QUSTION_NOT_FOUND);
        BeanUtils.copyProperties(question,questionDTO);
        if(question.getCreator()!=null)
            questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
        return questionDTO;
    }

//    public List<QuestionDTO> list() {
//        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
//        for(Question question:questionMapper.list()){
//            QuestionDTO questionDTO = new QuestionDTO();
//            BeanUtils.copyProperties(question,questionDTO);
//            if(question.getCreator()==null) continue;
//            questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
//            questionDTOList.add(questionDTO);
//        }
//        return questionDTOList;
//    }

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        Integer offset = size*(page-1);

        //排序 Example
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_modified desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        for(Question question:questionList){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            if(question.getCreator()==null) continue;
            questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPageArr(totalCount,page,size);


        return paginationDTO;
    }
    public PaginationDTO list(Long userId,Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        Integer offset = size*(page-1);
        //userId Example
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        questionExample.setOrderByClause("gmt_modified desc");
        //
        for(Question question:questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size))){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            if(question.getCreator()==null) continue;
            questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        paginationDTO.setPageArr(totalCount,page,size);


        return paginationDTO;
    }

    public void createOrUpdate(Question question) {

        if (question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else{
            //updateQuestion
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setContent(question.getContent());
            updateQuestion.setTag(question.getTag());
            // update example
            QuestionExample updateExample = new QuestionExample();
            updateExample.createCriteria().andIdEqualTo(question.getId());
            // update
            int updated = questionMapper.updateByExampleSelective(updateQuestion,updateExample);
            if (updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUSTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question record = new Question();
        record.setId(id);
        record.setViewCount(1);
        questionExtMapper.incView(record);

    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        List<QuestionDTO> relatedQuestions = new ArrayList<>();
        if (StringUtils.isBlank(questionDTO.getTag())){
            return relatedQuestions;
        }
        String[] tags = StringUtils.split(questionDTO.getTag(),',');
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        relatedQuestions = questions.stream().map(q->{
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
//            questionDTO1.setUser(userMapper.selectByPrimaryKey(q.getCreator()));
            return questionDTO1;
        }).collect(Collectors.toList());
        return relatedQuestions;
    }
}
