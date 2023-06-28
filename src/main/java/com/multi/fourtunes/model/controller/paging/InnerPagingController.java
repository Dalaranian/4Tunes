package com.multi.fourtunes.model.controller.paging;

import javax.servlet.http.HttpSession;

import com.multi.fourtunes.model.biz.MyPageBiz;
import com.multi.fourtunes.model.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.AdminpageBiz;
import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.biz.LoginBiz;

import java.util.Date;
import java.util.List;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.UserDto;

// 프론트 작성의 편의를 위한 임시 페이징 클래스입니다. 

@Controller
@RequestMapping("/innerpaging")
public class InnerPagingController {
	
	@Autowired
	private AdminpageBiz adminpageBiz;
	
	@Autowired
	private LoginBiz loginBiz;

	@Autowired MyPageBiz myPageBiz;
	// 로그인 페이지

	// 회원가입 페이지로 전환
	@GetMapping("/login/join")
	public String gotoLoginJoin() {
		return "login_join";
	}

	// 마이페이지

	// 내 정보 보기로 전환
	@GetMapping("/mypage/user")
	public String gotoMyPageUser(Model model, HttpSession session) {
	    String[] keywordList = loginBiz.getKeyword();
	    UserDto currentUser = (UserDto) session.getAttribute("login");
	    System.out.println(currentUser.getUser_no());
	    Date subscriptionEndDate = loginBiz.getSubscriptionEndDate(currentUser.getUser_no());
	    String userKeyword = loginBiz.getUserKeyword(currentUser.getUser_no());
	    StringBuilder myKeyword = new StringBuilder();
	    
	    // 내 회원등급 조회
	    String grade = adminpageBiz.selectGrade(currentUser.getUser_no());
	    model.addAttribute("grade", grade);
	    model.addAttribute("subscriptionEndDate", subscriptionEndDate);
	    model.addAttribute("keywordlist", keywordList);
	    model.addAttribute("userkeyword", userKeyword);
	    return "mypage_user";
	}
	

	//내 활동내역 조회
	@GetMapping("/mypage/communityContent")
	public String gotoMyPageCommunity(Model model, HttpSession session) {
		UserDto currentUser = (UserDto) session.getAttribute("login");
		List<CommunityDto> communityContent = myPageBiz.getUserMyContentAll(currentUser.getUser_no());
		List<CommentDto> communityComment = myPageBiz.getComments(currentUser.getUser_no());
		System.out.println(communityComment);
		model.addAttribute("communityComment", communityComment);
		model.addAttribute("communityContent", communityContent);
		return "mypage_community";
	}
	
	// 커뮤니티
	@GetMapping("/mypage/community")
	public String gotoCommmunity() {
		return "community_list";
	}
	// 글 작성 페이지로 이동
	@GetMapping("/community/write")
	public String gotoCommunityWrite() {
		return "community_write";
	}

	@GetMapping("/community/detail")
	public String gotoCommunityDetail() {
		return "community_detail";
	}

	// 메인페이지 재생목록

	// 내 재생목록으로 이동
	@GetMapping("/playlist/mine")
	public String gotoMyPlayList() {
		return "playlist_mine";
	}

	// public 재생목록으로 이동
	@GetMapping("/playlist/public")
	public String gotoPlaylistPublic() {
		return "playlist_public";
	}

	// 오늘의 선곡으로 이동
	@GetMapping("/playlist/suggested")
	public String gotoPlayListSuggested() {
		return "playlist_todaypick";
	}
}
