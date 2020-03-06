package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    private GithubProvider ghProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO stDto = new AccessTokenDTO();
        stDto.setCode(code);
        stDto.setState(state);
        stDto.setClient_id(clientId);
        stDto.setRedirect_uri(redirectUri);
        stDto.setClient_secret(clientSecret);

        String accessToken = ghProvider.getAccessToken(stDto);
        GithubUser ghUser = ghProvider.getUser(accessToken);

        if (ghUser != null) {
            User user = new User();
            //set user
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(ghUser.getName());
            user.setAccountId(String.valueOf(ghUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl(ghUser.getAvatar_url());
            user.setBio(ghUser.getBio());
            userMapper.insert(user);
            // cookie
            response.addCookie(new Cookie("token",token));
            //重定向
            return "redirect:/";
        } else {
            return "redirect:/";
        }

    }
}
