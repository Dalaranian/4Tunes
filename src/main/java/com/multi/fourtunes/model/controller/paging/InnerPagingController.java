package com.multi.fourtunes.model.controller.paging;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/innerpaging")
public class InnerPagingController {

	// 로그인 페이지
	
	// 회원가입 페이지로 전환
	@GetMapping("/login/join")
	public String gotoLoginJoin() {
		return "login_join";
	}

	// 마이페이지
	
	// 내 정보 보기로 전환
	@GetMapping("/mypage/user")
	public String gotoMyPageUser() {
		return "mypage_user";
	}
	
	// 내 활동 조회로 전환
	@GetMapping("/mypage/community")
	public String gotoMyPageCommmunity() {
		return "mypage_community";
	}
}
