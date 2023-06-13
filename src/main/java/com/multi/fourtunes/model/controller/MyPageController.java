package com.multi.fourtunes.model.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.fourtunes.model.biz.KeywordBiz;
import com.multi.fourtunes.model.dto.UserDto;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
	
	@Autowired
	private KeywordBiz keywordBiz;
	
	@PostMapping("/updateuser")
	@Transactional
	public String updateUser(UserDto dto, @RequestParam("selected_keyword") List<String> selectedKeywords, HttpSession session) {
	    // 기존 선택한 키워드 삭제
		UserDto currentUser = (UserDto) session.getAttribute("login");
	    keywordBiz.deleteUserKeyword(currentUser.getUser_id());
	    // 새로 선택한 키워드 추가
	    for (String keyword : selectedKeywords) {
	        keywordBiz.insertKeyword(keyword, currentUser.getUser_id());
	    }
	    
	    return "redirect:/nav/mypage"; // 마이페이지로 리다이렉트합니다.
	}
}
