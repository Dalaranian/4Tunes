	package com.multi.fourtunes.model.controller;
	
	import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.biz.KeywordBiz;
import com.multi.fourtunes.model.biz.LoginBiz;
import com.multi.fourtunes.model.dto.CommunityDto;
	
	@Controller
	@RequestMapping("/nav")
	public class NavController {
		
		@Autowired
		private CommunityBiz communityBiz;
	
		@Autowired
		private LoginBiz loginBiz;
		
		// 로그인 페이지로 이동
		@GetMapping("/login")
		public String gotoLogin() {
			return "login_login";
		}
	
		// 마이페이지로 이동
		@GetMapping("/mypage")
		public String gotoMyPage(Model model, HttpSession session) {
		    if (session.getAttribute("login") != null) {
		    	String[] keywordList = loginBiz.getKeyword();
		    	model.addAttribute("keywordlist", keywordList);
		        return "mypage_user";
		    } else {
		        model.addAttribute("error", "로그인이 필요합니다.");
		        return "login_login";
		    }
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
		@RequestMapping("/community")
		public String getCommunityList(Model model) {
			List<CommunityDto> communityList = communityBiz.getAll();
			model.addAttribute("communityList", communityList);
			return "community_list";
		}
	
		// 멤버쉽 페이지로 이동
		@GetMapping("/membership")
		public String gotoMembership() {
			return "membership_join";
		}
	
		// 검색 기능(차후 기능구현시 restcontroller 활용하여 수정)
		@GetMapping("/search")
		public String gotoSearch() {
			return "playlist_searchresult";
		}
	
	}
