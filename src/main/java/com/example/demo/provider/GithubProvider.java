package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO dto) {
        MediaType mediaType = MediaType.get("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String gitAuthUrl = "https://github.com/login/oauth/access_token";
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));
        Request request = new Request.Builder()
                .url(gitAuthUrl).post(body).build();
        try (
                Response response = client.newCall(request).execute()
        ) {
            String str1 = response.body().string();
            String[] split = str1.split("&");
            String tokenString = split[0];
            String token = tokenString.split("=")[1];
//            System.out.println(str1 + "\ntoken" + token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        String getUserUrl = "http://api.github.com/user?access_token=" + accessToken;
        Request request = new Request.Builder()
                .url(getUserUrl).build();
        try (
                Response response = client.newCall(request).execute();
        ) {
            String str = response.body().string();
            GithubUser ghUser = JSON.parseObject(str, GithubUser.class);
            return ghUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
