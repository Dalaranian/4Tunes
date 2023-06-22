package com.multi.fourtunes.model.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.apis.OpenAiApi;
import com.multi.fourtunes.model.biz.AdminpageBiz;
import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.biz.LoginBiz;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.UserDto;

@Controller
@RequestMapping("/nav")
public class NavController {
	
	@Autowired
	private AdminpageBiz adminpageBiz;

	@Autowired
	private CommunityBiz communityBiz;

	@Autowired
	private LoginBiz loginBiz;
	
	@Autowired
    OpenAiApi openAiApi;

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
			UserDto currentUser = (UserDto) session.getAttribute("login");
			System.out.println(currentUser);
			String[] userKeyword = loginBiz.getUserKeyword(currentUser.getUser_no());
			
			// 결제 개월수 조회
			int subscriptionMonth = loginBiz.getSubscriptionMonth(currentUser.getUser_no());
			//System.out.println("month : " + subscriptionMonth);
			if(subscriptionMonth == 0) {
				model.addAttribute("subscriptionEndDate", null);
			} else {
				LocalDate currentDate = LocalDate.now();
				LocalDate subscriptionEndDate = currentDate.plusMonths(subscriptionMonth);
				//System.out.println("만료날짜는: " + subscriptionEndDate);
				model.addAttribute("subscriptionEndDate", subscriptionEndDate);
			}
			
			
			StringBuilder myKeyword = new StringBuilder();
			for (String str : userKeyword) {
				myKeyword.append(str + " ");
			}
			
			// 내 회원등급 조회
		    String grade = adminpageBiz.selectGrade(currentUser.getUser_no());
		    model.addAttribute("grade", grade);
			model.addAttribute("keywordlist", keywordList);
			model.addAttribute("userkeyword", myKeyword.toString());
			return "mypage_user";
		} else {
			model.addAttribute("error", "로그인이 필요합니다.");
			return "login_login";
		}
	}

	// 맞춤 추천 페이지로 이동
	@GetMapping("/suggested")
	public String gotoSuggested(HttpSession session, Model model) {
		// 로그인 되어있으면 해당 회원의 키워드 불러오기
		if (session.getAttribute("login") != null) {
			UserDto user = (UserDto)session.getAttribute("login");
			String[] userKeyword = loginBiz.getUserKeyword(user.getUser_no());
			
			StringBuilder myKeyword = new StringBuilder();
			for (String str : userKeyword) {
				//System.out.println("String[] : " + str);
				myKeyword.append(str + " ");
			}

			model.addAttribute("userkeyword", myKeyword.toString());
			
			// GPT에게 노래추천받기
			openAiApi.suggestedSong(userKeyword);
			
			//return "redirect:/api/gpt";
			return "playlist_suggested";
			
		} else {
			return "login_login";
		}	
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

	// 관리자 페이지를 위한 임시페이징입니다.
	@GetMapping("/adminpage")
	public String gotoAdminPage() {
		return "adminpage_main";
	}

}
