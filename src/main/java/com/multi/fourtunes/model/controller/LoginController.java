package com.multi.fourtunes.model.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.fourtunes.model.biz.FaceloginBiz;
import com.multi.fourtunes.model.biz.LoginBiz;
import com.multi.fourtunes.model.biz.UserBiz;
import com.multi.fourtunes.model.dto.UserDto;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private FaceloginBiz faceloginBiz;
	
	@Autowired
	private LoginBiz loginBiz;
	
	@Autowired
	private UserBiz memberBiz;

	@GetMapping("/sociallogin")
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
	
	/**
	 * 소셜로그인이 아닌 일반적인 로그인을 처리
	 * @param session
	 * @param dto
	 * @return
	 */
	@PostMapping("/login")
	public String login(HttpSession session, UserDto dto) {
//		System.out.println("LoginController 진입 \n" + dto.toString());
		UserDto res = memberBiz.login(dto);
//		System.out.println("리턴받은 dto : " + res.toString());
		if (res != null) {
			session.setAttribute("currentUser", res);
			return "index";
		} else {
			return "login_login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("currentUser");
		return "index";
	}
}