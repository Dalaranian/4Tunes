package com.multi.fourtunes.model.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.fourtunes.model.apis.ManiaDbApi;
import com.multi.fourtunes.model.biz.AdminpageBiz;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.AdminCommentReportDto;
import com.multi.fourtunes.model.dto.AdminCommunityReportDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Slf4j
@Controller
@RequestMapping("/adminpage")
public class AdminpageController {
	
		@Autowired
		private AdminpageBiz adminpageBiz;
		
		@Autowired
		ManiaDbApi mania;
		
		@Autowired
		SongRepository songRepository;
		
		// 회원정보 조회로 이동
		@GetMapping("/user")
		public String gotoAdminpageUser(Model model) {
			List<UserDto> userList = adminpageBiz.selectList();
			model.addAttribute("userList", userList);
			
			return "adminpage_user";
		}
	
		@GetMapping("/update/{user_no}")
		public String updateUserGrade(@PathVariable int user_no) {
			// 해당 회원의 등급을 조회함
			String grade = adminpageBiz.selectGrade(user_no);
			
			if(grade.equals("FREE")) {  // 조회한 회원등급이 free이면 paid로 변경
				adminpageBiz.updateGradePaid(user_no);
			} else {  // 조회한 회원등급이 paid이면 free로 변경
				adminpageBiz.updateGradeFree(user_no);
			}
			
			return "redirect:/adminpage/user";
		}
		
		@GetMapping("/delete/{user_no}")
		public String deleteUser(@PathVariable int user_no) {
			adminpageBiz.deleteUser(user_no);
			
			return "redirect:/adminpage/user";
		}
		
		@GetMapping("/search")
		public String searchUser(@RequestParam("username") String name, Model model) {
			List<UserDto> userList = adminpageBiz.searchUser(name);
			//// log.info("userList: " + userList);
			model.addAttribute("searchUserList", userList);
			
			return "adminpage_searchuser";
		}
		
		
		// 게시글 관리로 이동
		@GetMapping("/community")
		public String gotoAdminpageCommunity(Model model) {
			// 신고당한 게시글과 사용자를 조회
			List<AdminCommunityReportDto> report = adminpageBiz.selectReport();
			//// log.info("report: " + report);
			model.addAttribute("report", report);
			
			return "adminpage_community";
		}
		
		@GetMapping("/confirm/{board_no}")
		public String confirm(@PathVariable int board_no) {
			//// log.info("board_no는? : " + board_no);
			// 관리자가 문제없음 클릭 시, 해당 게시글의 신고누적횟수를 0으로 변경
			if(adminpageBiz.confirmReport(board_no) > 0) {
				// COMMUNITY_REPORT 테이블에서 해당 게시글의 신고내역을 지움
				adminpageBiz.deleteReport(board_no);
			}
			
			return "redirect:/adminpage/community";
		}
		
		
		// 댓글 관리로 이동
		@GetMapping("/comment")
		public String gotoAdminpageComment(Model model) {
			// 신고당한 댓글과 사용자를 조회
			List<AdminCommentReportDto> report = adminpageBiz.selectReportComment();
			//// log.info("report: " + report);
			model.addAttribute("report", report);
			return "adminpage_comment";
		}
		
		@GetMapping("/confirmComment/{comment_no}")
		public String confirmComment(@PathVariable int comment_no) {
			//// log.info("comment_no는? : " + comment_no);
			// 관리자가 문제없음 클릭 시, 해당 댓글의 신고누적횟수를 0으로 변경
			if(adminpageBiz.confirmReportComment(comment_no) > 0) {
				// COMMUNITY_REPORT 테이블에서 해당 댓글의 신고내역을 지움
				adminpageBiz.deleteReportComment(comment_no);
			}
			
			return "redirect:/adminpage/comment";
		}
		
		// 4Tunes 선곡 관리로 이동
		@GetMapping("/todaypick")
		public String gotoAdminpageTodaypick() {
			return "adminpage_todaypick";
		}
		
		// 관리자가 입력한 노래를 ManiaDB에 검색
		@GetMapping("/searchsong")
		public String searchSong(@RequestParam("title") String title, Model model) {
			mania.setPrompt(title);
			mania.setType(false);
			
			ArrayList<SongDto> searchResult = mania.search();
			// log.info("검색 결과: " + searchResult);
			
			// YoutubeApi를 통해 SongLink 불러오기
			ArrayList<SongDto> finalResult = adminpageBiz.setSonglink(searchResult, title);
			
			model.addAttribute("searchRes", finalResult);
			
			return "adminpage_todaypick";
		}
		
		// 관리자가 선택한 노래를 DB에 저장
		@GetMapping("/insertsong")
		@ResponseBody
		public String insertSong(@RequestParam("songDto") String dto, @RequestParam("playlist") String playlist) {
			
			// log.info("SongDto: " + dto);
			// log.info("playlist: " + playlist);
			
			// 관리자가 선택한 String 타입의 songDto와 playlist를 biz로 넘김
			String res = adminpageBiz.insertSong(dto, playlist);
			
			return res;
		}
}





