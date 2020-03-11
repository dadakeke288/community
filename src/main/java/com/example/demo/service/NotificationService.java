package com.example.demo.service;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.dto.PaginationDTO;
import com.example.demo.enums.NotificationEnum;
import com.example.demo.enums.NotificationStatusEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Notification;
import com.example.demo.model.NotificationExample;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    public PaginationDTO list(Long id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        List<NotificationDTO> notificationDTOS = new ArrayList<NotificationDTO>();
        Integer offset = size*(page-1);
        //userId Example
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        notificationExample.setOrderByClause("status asc,gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample,new RowBounds(offset,size));

        if (notifications.size()==0) return paginationDTO;
        for (Notification notification:notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOf(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        paginationDTO.setPageArr(totalCount,page,size);
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification =  notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (notification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOf(notification.getType()));
        return notificationDTO;
    }
}
