package com.multi.fourtunes.model.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
	private CommunityBiz communityBiz;
	
	@RequestMapping("/communityContent")
	public String getCommunityList(Model model, HttpSession session) {
		UserDto currentUser = (UserDto) session.getAttribute("login");
		List<CommunityDto> communityContent = communityBiz.getUserMyContentAll(currentUser.getUser_no());
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

		@PostMapping("/updateuser")
		@Transactional
		public String updateUser(UserDto dto, @RequestParam("selected_keyword") List<String> selectedKeywords, HttpSession session) {
		    // 기존 선택한 키워드 삭제
			UserDto currentUser = (UserDto) session.getAttribute("login");
		    keywordBiz.deleteUserKeyword(currentUser.getUser_id());
		    // 새로 선택한 키워드 추가
		    for (String keyword : selectedKeywords) {
		        keywordBiz.insertKeyword(keyword, currentUser.getUser_id());
		    }
		    
		    return "redirect:/nav/mypage"; // 마이페이지로 리다이렉트합니다.
		}

}
