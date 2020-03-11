package com.example.demo.service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.enums.NotificationEnum;
import com.example.demo.enums.NotificationStatusEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.*;
import com.example.demo.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    public List<CommentDTO> listByTargetId(Long quetionId, Integer page, Integer size, CommentTypeEnum typeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(quetionId)
                .andTypeEqualTo(typeEnum.getType());
        commentExample.setOrderByClause("gmt_create asc");
        List<Comment> comments =  commentMapper.selectByExample(commentExample);
        if (comments==null || comments.size()==0){
            return new ArrayList<>();
        }
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        //得到评论的创建者
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User> userMap= users.stream().collect(Collectors.toMap(user -> user.getId(), user ->user));

        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }


    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //回复主贴
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question==null) throw new CustomizeException(CustomizeErrorCode.QUSTION_NOT_FOUND);
            commentMapper.insert(comment);

            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
            //增加消息提醒
            createNotify(comment, question.getTitle(), dbComment.getCommentator(),commentator.getName(), NotificationEnum.REPLY_COMMENT);
        }else{
            //回复主贴
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question==null) throw new CustomizeException(CustomizeErrorCode.QUSTION_NOT_FOUND);
            commentMapper.insert(comment);
            Question dbQuestion = new Question();
            dbQuestion.setId(question.getId());
            dbQuestion.setCommentCount(1);
            questionExtMapper.incCommentCount(dbQuestion);
            //增加消息提醒
            createNotify(comment,question.getTitle(),question.getCreator(),commentator.getName(),NotificationEnum.REPLY_QUESTION );

        }
    }

    private void createNotify(Comment comment, String outerTitle, Long receiver, String notifierName, NotificationEnum type) {
        //增加消息提醒
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type.getType());
        notification.setOuterid(comment.getParentId());
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }
}
