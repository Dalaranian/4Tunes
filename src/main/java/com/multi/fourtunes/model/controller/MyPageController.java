package com.multi.fourtunes.model.controller;

import java.util.List;

import com.multi.fourtunes.model.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.dto.CommunityDto;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

	@Autowired
	private CommunityBiz communityBiz;

	// 상세 페이지로 이동
	@RequestMapping("/detail/{boardNo}")
	public String getCommunityDetail(@PathVariable int boardNo, Model model) {
		CommunityDto community = communityBiz.get(boardNo);
		communityBiz.incrementViewCount(boardNo);
		model.addAttribute("community", community);
		// 댓글
		List<CommentDto> commentList = communityBiz.getComments(boardNo);
		model.addAttribute("commentList", commentList);
		return "community_detail";
	}

}
