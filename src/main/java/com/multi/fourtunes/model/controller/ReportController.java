package com.multi.fourtunes.model.controller;

import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.dto.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private CommunityBiz communityBiz;

    @GetMapping("/checkReportStatus")
    public ResponseEntity<Map<String, Boolean>> checkReportStatus(@RequestParam int boardNo, HttpSession session) {
        // 세션에서 로그인된 사용자 정보를 가져옴
        UserDto loginUser = (UserDto) session.getAttribute("login");

        // 로그인된 사용자가 있을 경우에만 신고 여부 확인
        boolean alreadyReported = false;
        if (loginUser != null) {
            alreadyReported = communityBiz.isReported(loginUser.getUser_no(), boardNo);
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("reported", alreadyReported);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
