package com.multi.fourtunes.model.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.multi.fourtunes.model.biz.ChartBiz;
import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.biz.LoginBiz;
import com.multi.fourtunes.model.biz.SuggestBiz;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.SongDto;
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
	
	@Autowired
	SuggestBiz suggestBiz;

	@Autowired
	private ChartBiz chartBiz;
	
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
			String userKeyword = loginBiz.getUserKeyword(currentUser.getUser_no());
			
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
			
			// 내 회원등급 조회
		    String grade = adminpageBiz.selectGrade(currentUser.getUser_no());
		    model.addAttribute("grade", grade);
			model.addAttribute("keywordlist", keywordList);
			model.addAttribute("userkeyword", userKeyword);
			return "mypage_user";
		} else {
			model.addAttribute("error", "로그인이 필요합니다.");
			return "login_login";
		}
	}

	// 맞춤 추천 페이지로 이동
	@GetMapping("/suggested")
	public String gotoSuggested(HttpSession session, Model model) {
		// 현재 로그인 되어있는 회원의 키워드 조회하기
		if (session.getAttribute("login") != null) {
			UserDto user = (UserDto)session.getAttribute("login");
			String userKeyword = loginBiz.getUserKeyword(user.getUser_no());
			
			// StringBuilder : String을 합치는 역할, append()를 통해 문자열을 합쳐준다.
			/*
			 * StringBuilder myKeyword = new StringBuilder(); for (String str : userKeyword)
			 * { myKeyword.append(str + " "); }
			 */

			// 조회한 해당 회원의 키워드를 model에 담아줌
			model.addAttribute("userkeyword", userKeyword);
			
			// OpenAI 에게 노래추천받기 (키워드가 담겨있는 String 배열을 매개변수로 전달)
			// 추천받은 노래 10곡을 songs에 담아줌
			ArrayList<SongDto> songs = openAiApi.suggestedSong(userKeyword);
			if(songs.size() == 0) {
				model.addAttribute("suggestResult", "검색 결과가 없습니다.");
			} else {
				// ManiaDB에 추천받은 노래 검색 (노래 10곡의 SongDto가 담겨있는 ArrayList를 매개변수로 전달)
				ArrayList<SongDto> finalRes = suggestBiz.searchSuggestedSong(songs);
				model.addAttribute("suggestResult", finalRes);
			}
			
			//return "redirect:/api/gpt";
			return "playlist_suggested";
			
		} else {
			return "login_login";
		}	
	}
	
	// 인기 차트 페이지로 이동
	@GetMapping("/chart")
	public String gotoChart(Model model) {
		List<SongDto> topSongs = chartBiz.getTop10Songs();

		// 전체 플레이리스트 횟수 정보 추가
		for (SongDto song : topSongs) {
			int playlistCount = chartBiz.getPlaylistCount(song.getSongNo());
			song.setPlaylistCount(playlistCount);

		}

		model.addAttribute("topSongs", topSongs);
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
