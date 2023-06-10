package com.multi.fourtunes.model.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.dto.CommunityDto;

@Controller
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private CommunityBiz communityBiz;

//	@RequestMapping("/community")
//	public String getCommunityList(Model model) {
//		List<CommunityDto> communityList = communityBiz.getAll();
//		model.addAttribute("communityList", communityList);
//		return "community_list";
//	}

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

	@RequestMapping("/create")
	public String communityCreate() {

		return "community_detail";
	}
}
