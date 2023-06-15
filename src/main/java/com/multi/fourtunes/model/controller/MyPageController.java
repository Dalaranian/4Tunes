package com.multi.fourtunes.model.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.multi.fourtunes.model.biz.LoginBiz;
import com.multi.fourtunes.model.biz.MyPageBiz;
import com.multi.fourtunes.model.dao.MyPageDao;
import com.multi.fourtunes.model.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.UserDto;

import com.multi.fourtunes.model.biz.KeywordBiz;


@Controller
@RequestMapping("/mypage")
public class MyPageController {

	@Autowired
	private KeywordBiz keywordBiz;

	@Autowired
	private LoginBiz loginBiz;

	@Autowired
	private CommunityBiz communityBiz;

	@Autowired
	private MyPageBiz myPageBiz;

	//내 활동 조회
//	@RequestMapping("/communityContent")
//		public String getCommunityList(Model model, HttpSession session) {
//			UserDto currentUser = (UserDto) session.getAttribute("login");
//			List<CommunityDto> communityContent = myPageBiz.getUserMyContentAll(currentUser.getUser_no());
//			List<CommentDto> communityComment = myPageBiz.getComments(currentUser.getUser_no());
//			System.out.println(communityComment);
//			model.addAttribute("communityComment", communityComment);
//			model.addAttribute("communityContent",communityContent);
//			return "mypage_community";
//	}


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
