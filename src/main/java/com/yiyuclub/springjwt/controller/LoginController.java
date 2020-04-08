package com.yiyuclub.springjwt.controller;

import com.yiyuclub.springjwt.config.JJwtUtils;
import com.yiyuclub.springjwt.config.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("login")
    public String login(String username,String pwd){
//        String token = JwtUtils.getToken(username, pwd);

        String token = JJwtUtils.getToken(username, pwd);
        return token;
    }

    @PostMapping("logi")
    public void logi(){
        System.out.println("hahahaha");
    }
}
