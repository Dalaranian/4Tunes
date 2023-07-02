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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.multi.fourtunes.model.apis.AnalysisApi;
import com.multi.fourtunes.model.apis.OpenAiApi;
import com.multi.fourtunes.model.biz.AdminpageBiz;
import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.biz.LoginBiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.PlaylistEntity;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.entity.UserEntity;
import com.multi.fourtunes.model.jpa.repository.PlaylistRepository;
import com.multi.fourtunes.model.jpa.repository.UserRepository;
import com.multi.fourtunes.model.mapper.KeywordMapper;

// 프론트 작성의 편의를 위한 임시 페이징 클래스입니다. 

@Controller
@RequestMapping("/innerpaging")
public class InnerPagingController {
	@Autowired
    private PlaylistRepository playlistRepository;
	
	@Autowired
    AnalysisApi analysisApi;

	@Autowired
	private AdminpageBiz adminpageBiz;
	
	@Autowired
	private LoginBiz loginBiz;
	
	@Autowired
	private KeywordMapper keywordMapper;

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
	
	
	@GetMapping("/mypage/analysis")
	public String gotoAnalysis(HttpSession session, Model model) {
	    // 현재 로그인 되어있는 회원의 플레이리스트의 모든 노래 목록 조회하기
	    if (session.getAttribute("login") != null) {
	        UserDto user = (UserDto) session.getAttribute("login");
	        int userNo = user.getUser_no();

	        PlaylistEntity playlist = playlistRepository.findPlaylistByUserNo(userNo);
	        if (playlist != null) {
	            List<SongEntity> songs = playlistRepository.findAllSongsByPlaylistNo(playlist.getPlaylistNo());
	            List<String> playlistSongsAndArtists = new ArrayList<>();
	            for (SongEntity song : songs) {
	                String songAndArtist = song.getSongTitle() + "-" + song.getSongArtist();
	                playlistSongsAndArtists.add(songAndArtist);
	            }
	            // 조회한 해당 회원의 모든 노래를 "노래제목-가수" 형식으로 목록에 담음
	            
	            // 노래 목록 콘솔 출력
	            for (String song : playlistSongsAndArtists) {
	                System.out.println(song);
	            }
	            String[] keywords = keywordMapper.getAllKeyword();
	            
	            // 키워드 목록 콘솔 출력
	            System.out.println("Keywords:");
	            for (String keyword : keywords) {
	                System.out.println(keyword);
	            }

	            model.addAttribute("playlistSongsAndArtists", playlistSongsAndArtists);
	            model.addAttribute("keywords", keywords);

	            
	            try {
	                // API 호출
	                List<Double> ratios = analysisApi.getKeywordRatios(playlistSongsAndArtists.toArray(new String[0]));

	                // 키워드 비율 콘솔 출력
	                System.out.println("Keyword Ratios:");
	                for (int i = 0; i < keywords.length; i++) {
	                    System.out.println(keywords[i] + ": " + ratios.get(i));
	                }

	                model.addAttribute("ratios", ratios);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            
	            return "mypage_analysis";
	        }
	    }
	    return "login_login";
	}

	

	
}
