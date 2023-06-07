package com.multi.fourtunes.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.fourtunes.model.biz.LoginBiz;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginBiz loginBiz;

	@GetMapping("/sociallogin")
	public String socialLogin(@RequestParam("email") String email, @RequestParam("name") String name, Model model) {
		boolean isUserExist = loginBiz.checkUserExist(email, name);
		if (isUserExist) {
			// DB에 있는 이메일이면 로그인 진행
			// 로그인 처리 로직 구현
			
			
			
			return "index"; // 로그인 후 이동할 페이지
		} else {
			// DB에 없는 이메일이면 ID 입력칸과 이름 입력칸으로 이동
			// 회원 가입 페이지로 이동
			model.addAttribute("email", email);
			model.addAttribute("name", name);
			
			String[] keywordList = loginBiz.getKeyword();
			model.addAttribute("keywordlist", keywordList);			
			return "login_join";
		}
	}

	/**
	 * 회원가입 페이지에, MODEL 에 키워드 배열을 담아서 전달
	 * @param model
	 * @return 회원가입페이지로 전환
	 */
	@GetMapping("/join")
	public String join(Model model) {
		System.out.println("join 진입");
		String[] keywordList = loginBiz.getKeyword();
		for(String str:keywordList) {
			System.out.println("키워드 : " + str);
		}
		model.addAttribute("keywordlist", keywordList);
		return "login_join";
	}
}