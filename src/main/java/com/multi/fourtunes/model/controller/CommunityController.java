package com.multi.fourtunes.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.dto.CommunityDto;

@Controller
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private CommunityBiz communityBiz;

	
	@RequestMapping("/list")
	public String getCommunityList(Model model) {
		List<CommunityDto> communityList = communityBiz.getAll();
		model.addAttribute("communityList", communityList);
		return "community_list";
	}

	@RequestMapping("/detail/{boardNo}")
	public String getCommunityDetail(@PathVariable int boardNo, Model model) {
	    CommunityDto community = communityBiz.get(boardNo);
	    model.addAttribute("community", community);
	    return "community_detail";
	}
}
