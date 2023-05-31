package com.multi.fourtunes.model.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.UserBiz;
import com.multi.fourtunes.model.dto.UserDto;

@Controller
@RequestMapping("/member")
public class UserController {
	
	@Autowired
	private UserBiz memberBiz;
	
	@PostMapping("/login")
	public String login(HttpSession session, UserDto dto) {
		UserDto res = memberBiz.login(dto);
		
		if(res != null) {
			session.setAttribute("login", res);
		} else {
			return "login_login";
		}
		return "index";
	}
	
}
