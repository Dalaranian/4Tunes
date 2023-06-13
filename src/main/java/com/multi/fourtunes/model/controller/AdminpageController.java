package com.multi.fourtunes.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.AdminpageBiz;
import com.multi.fourtunes.model.dto.UserDto;

@Controller
@RequestMapping("/adminpage")
public class AdminpageController {
	
		@Autowired
		private AdminpageBiz adminpageBiz;
		
		// 회원정보 조회로 이동
		@GetMapping("/user")
		public String gotoAdminpageUser(Model model) {
			List<UserDto> userList = adminpageBiz.selectList();
			model.addAttribute("userList", userList);
			
			return "adminpage_user";
		}
		
		// 게시글 관리로 이동
		@GetMapping("/community")
		public String gotoAdminpageCommunity() {
			return "adminpage_community";
		}
		
		// 댓글 관리로 이동
		@GetMapping("/comment")
		public String gotoAdminpageComment() {
			return "adminpage_comment";
		}

}
