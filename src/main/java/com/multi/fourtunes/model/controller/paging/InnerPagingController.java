package com.multi.fourtunes.model.controller.paging;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/innerpaging")
public class InnerPagingController {
	// 로그인 페이지
	
	// 회원 가입 페이지로 이동
	@GetMapping("/join")
	public String gotoJoin() {
		System.out.println("회원가입페이지로 이동");
		return "login_join";
	}
}
