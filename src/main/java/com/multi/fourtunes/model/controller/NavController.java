package com.multi.fourtunes.model.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nav")
public class NavController {
	
	// 루트 요청(페이지를 index.html 로 보내기)
	@RequestMapping("/")
	public String root() {
		return "index";
	}
	
	// 로그인 페이지로 이동
	@GetMapping("/login")
	public String gotoLogin() {
		return "login_login";
	}
	
	// 마이페이지로 이동
	@GetMapping("/mypage")
	public String gotoMyPage() {
		return "mypage_user";
	} 
	
	// 맞춤 추천 페이지로 이동
	@GetMapping("/suggested")
	public String gotoSuggested() {
		return "playlist_suggested";
	}
	
	// 인기 차트 페이지로 이동
	@GetMapping("/chart")
	public String gotoChart() {
		return "chartpage_main";
	}
	
	// 커뮤니티 페이지로 이동
	@GetMapping("/community")
	public String gotoCommunity() {
		return "community_list";
	}
	
	// 멤버쉽 페이지로 이동
	@GetMapping("/membership")
	public String gotoMembership() {
		return "membership_join";
	}
	
}