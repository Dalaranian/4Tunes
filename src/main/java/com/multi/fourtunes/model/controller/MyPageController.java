package com.multi.fourtunes.model.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.dto.CommunityDto;

@Controller
@RequestMapping("/communityContent")
public class MyPageController {
	
	@Autowired
	private CommunityBiz communityBiz;
	
	@RequestMapping("/communityContent")
	public String getCommunityList(@PathVariable int userNo, Model model) {
		List<CommunityDto> communityContent = communityBiz.getUserMyContentAll(userNo);
	for(CommunityDto dto:communityContent) {
		System.out.println(dto);
	}
		model.addAttribute("communityContent",communityContent);
		return "mypage_community";
	}
	
	// 상세 페이지로 이동
		@RequestMapping("/detail/{boardNo}")
		public String getCommunityDetail(@PathVariable int boardNo, Model model) {
			CommunityDto community = communityBiz.get(boardNo);
			communityBiz.incrementViewCount(boardNo);
			model.addAttribute("community", community);
			return "community_detail";
		}

		@RequestMapping("/write")
		public String communityWrite(HttpSession session) {
			System.out.println("Session Contents: " + session.getAttribute("login"));
			return "community_write";
		}

		@PostMapping("/create")
	    public String communityCreate(@ModelAttribute("community") CommunityDto community) {
	        communityBiz.insert(community);
	        return "redirect:/nav/community";
	    }
	
}
