package com.multi.fourtunes.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.fourtunes.model.biz.FaceloginBiz;

@Controller
@RequestMapping("/facebook")
public class FaceloginController {

    @Autowired
    private FaceloginBiz faceloginBiz;

    @GetMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("name") String name) {
        boolean isUserExist = faceloginBiz.checkUserExist(email, name);
        if (isUserExist) {
            // DB에 있는 이메일이면 로그인 진행
            // 로그인 처리 로직 구현
            return "index"; // 로그인 후 이동할 페이지
        } else {
            // DB에 없는 이메일이면 ID 입력칸과 이름 입력칸으로 이동
            // 회원 가입 페이지로 이동
            return "login_join";
        }
    }
}