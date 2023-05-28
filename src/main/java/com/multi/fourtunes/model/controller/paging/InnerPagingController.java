package com.multi.fourtunes.model.controller.paging;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/innerpaging")
public class InnerPagingController {

	@GetMapping("/login/join")
	public String gotoJoin() {
		return "login_join";
	}

	@RequestMapping("/mypage")
	public class MyPage {

	}
}
