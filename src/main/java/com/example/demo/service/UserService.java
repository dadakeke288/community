package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createOrUpdate(User user) {
        User oldUser = userMapper.findByAccountId(user.getAccountId());
        if (oldUser==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        }else{
            oldUser.setGmtModified(System.currentTimeMillis());
            oldUser.setAvatarUrl(user.getAvatarUrl());
            oldUser.setName(user.getName());
            oldUser.setToken(user.getToken());
            oldUser.setBio(user.getBio());
            userMapper.update(user);
        }
    }
}
