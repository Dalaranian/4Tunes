package com.multi.fourtunes.model.controller.paging;

import javax.servlet.http.HttpSession;

import com.multi.fourtunes.model.biz.MyPageBiz;
import com.multi.fourtunes.model.dto.AnalysisKeywordDto;
import com.multi.fourtunes.model.biz.PlaylistBiz;
import com.multi.fourtunes.model.dto.CommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.multi.fourtunes.model.apis.AnalysisApi;
import com.multi.fourtunes.model.apis.OpenAiApi;
import com.multi.fourtunes.model.biz.AdminpageBiz;
import com.multi.fourtunes.model.biz.AnalysisBiz;
import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.biz.LoginBiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.PlaylistEntity;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.entity.UserEntity;
import com.multi.fourtunes.model.jpa.repository.PlaylistRepository;
import com.multi.fourtunes.model.jpa.repository.UserRepository;
import com.multi.fourtunes.model.mapper.KeywordMapper;

// 프론트 작성의 편의를 위한 임시 페이징 클래스입니다. 

@Slf4j
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
	
	@Autowired
	private AnalysisBiz analysisBiz;

    @Autowired
    PlaylistBiz playlist;
    
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
	    // log.debug(currentUser.getUser_no() + "");
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
		// log.debug(communityComment.toString());
		model.addAttribute("communityComment", communityComment);
		model.addAttribute("communityContent", communityContent);
		return "mypage_community";
	}
	
	// 커뮤니티
	@GetMapping("/mypage/community")
	public String gotoCommmunity() {
		return "community_list";
	}
	
	//연속 재생
	@GetMapping("/mypage/continuous_play")
	public String gotoContinuousPlay(Model model,HttpSession session) {
	    // 세션의 UserDto 불러오기
	    UserDto currentUser = (UserDto) session.getAttribute("login");	
	    String userNo=Integer.toString(currentUser.getUser_no());
	    List<SongDto> songs = playlist.getPlayListSongs(userNo);
	    // log.debug(songs.toString());
	    model.addAttribute("songs",songs);
		return "mypage_continuous_play";
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
		// 현재 로그인 되어있는 회원 조회
		if (session.getAttribute("login") != null) {
			UserDto user = (UserDto) session.getAttribute("login");
			int userNo = user.getUser_no();

			// 회원의 플레이리스트 노래 전체 갯수 조회
			List<PlaylistEntity> playlists = playlistRepository.findByUserUserNo(userNo);
			int totalSongs = 0;
			for (PlaylistEntity playlist : playlists) {
				totalSongs += playlist.getManageSongs().size();
			}

			// 플레이리스트 노래 SONG_AIKEYWORD 통계 조회
			// List<AnalysisKeywordDto> keywordStats = analysisBiz.getAIKeywordAnalysis(userNo);
			List<AnalysisKeywordDto> top3KeywordStats = analysisBiz.getAIKeywordAnalysis(userNo);

			// HotFix 타임리프 파싱 에러 처리
			if (totalSongs >= 3) {
				// 결과 model에 추가
				model.addAttribute("totalSongs", totalSongs);
				// model.addAttribute("keywordStats", keywordStats);
				model.addAttribute("top3KeywordStats", top3KeywordStats);

				return "mypage_analysis";
			} else {
				log.error("빈 플레이리스트가 통계 페이지 접근");
				return "index";
			}
		}
		
		return "login_login";
	}

}
