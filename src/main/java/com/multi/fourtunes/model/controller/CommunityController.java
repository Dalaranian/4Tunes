package com.multi.fourtunes.model.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommentReportDto;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.CommunityReportDto;
import com.multi.fourtunes.model.dto.UserDto;

@Controller
@RequestMapping("/community")
public class CommunityController {

	@Autowired
	private CommunityBiz communityBiz;

//	@RequestMapping("/community")
//	public String getCommunityList(Model model) {
//		List<CommunityDto> communityList = communityBiz.getAll();
//		model.addAttribute("communityList", communityList);
//		return "community_list";
//	}

	// 게시글 상세 페이지로 이동
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

	@RequestMapping("/write")
	public String communityWrite(HttpSession session) {
		System.out.println("Session Contents: " + session.getAttribute("login"));
		return "community_write";
	}

	@PostMapping("/create")
	public String communityCreate(@ModelAttribute("community") CommunityDto community) {
		communityBiz.insert(community);
		return "redirect:/nav/community";
	}

	// 게시글 수정
	@RequestMapping("/update/{boardNo}")
	public String communityUpdate(@PathVariable int boardNo, Model model, HttpSession session) {
		// 세션에서 로그인된 사용자 정보를 가져옴
		UserDto loginUser = (UserDto) session.getAttribute("login");

		// 로그인된 사용자가 있을 경우에만 글을 수정할 수 있음
		if (loginUser != null) {
			CommunityDto community = communityBiz.get(boardNo);

			// 작성자와 로그인된 사용자가 같을 경우에만 수정 가능
			if (community.getUser_name().equals(loginUser.getUser_name())) {
				model.addAttribute("community", community);
				return "community_update";
			}
		}
		// 작성자가 아니거나 로그인된 사용자가 없는 경우에는 상세 페이지로 리다이렉트
		return "redirect:/community/detail/" + boardNo;

	}

	@PostMapping("/update/{boardNo}")
	public String communityUpdatePost(@PathVariable int boardNo,
			@ModelAttribute("community") CommunityDto updatedCommunity) {
		// 업데이트 작업 수행
		communityBiz.update(updatedCommunity);

		// 상세 페이지로 리다이렉트
		return "redirect:/community/detail/" + boardNo;
	}

	// 게시글 삭제
	@PostMapping("/delete/{boardNo}")
	public String communityDelete(@PathVariable int boardNo, HttpSession session) {
		// 세션에서 로그인된 사용자 정보를 가져옴
		UserDto loginUser = (UserDto) session.getAttribute("login");

		// 로그인된 사용자가 있을 경우에만 글을 삭제할 수 있음
		if (loginUser != null) {
			CommunityDto community = communityBiz.get(boardNo);

			// 작성자와 로그인된 사용자가 같을 경우에만 삭제 가능
			if (community.getUser_name().equals(loginUser.getUser_name())) {

				// 먼저 해당 게시글에 달린 댓글들을 삭제
				communityBiz.deleteByBoardNo(boardNo);

				communityBiz.delete(boardNo);
				return "redirect:/nav/community/";
			}
		}

		// 삭제가 완료되지 않았거나 로그인된 사용자가 없는 경우, 해당 글의 상세 페이지로 리다이렉트
		return "redirect:/community/detail/" + boardNo;
	}

	// 댓글 작성
	@PostMapping("/comment/{boardNo}")
	public String addComment(@PathVariable int boardNo, @ModelAttribute("comment") CommentDto comment,
			HttpSession session) {
		UserDto loginUser = (UserDto) session.getAttribute("login");

		// 로그인된 사용자만 댓글 추가 가능
		if (loginUser != null) {
			comment.setUserNo(loginUser.getUser_no());
			comment.setBoardNo(boardNo);

			// 댓글 추가
			communityBiz.addComment(comment);
		}

		// 상세 페이지로 리다이렉트
		return "redirect:/community/detail/" + boardNo;
	}

	// 댓글 삭제
	@PostMapping("/delete/comment/{commentNo}")
	public String deleteComment(@PathVariable int commentNo, HttpSession session) {
		// 세션에서 로그인된 사용자 정보를 가져옴
		UserDto loginUser = (UserDto) session.getAttribute("login");

		// 로그인된 사용자가 있을 경우에만 댓글을 삭제할 수 있음
		if (loginUser != null) {
			CommentDto comment = communityBiz.getComment(commentNo);

			// 댓글의 작성자와 로그인된 사용자가 같을 경우에만 삭제 가능
			if (comment.getUserNo() == loginUser.getUser_no()) {
				int boardNo = comment.getBoardNo(); // 삭제된 댓글이 속한 게시글 번호

				// 댓글 삭제
				communityBiz.deleteComment(commentNo);

				// 게시글 상세 페이지로 리다이렉트
				return "redirect:/community/detail/" + boardNo;
			}
		}
		// 삭제가 완료되지 않았거나 로그인된 사용자가 없는 경우, 리스트 페이지로 리다이렉트
		return "redirect:/nav/community/";

	}

//	// 게시글 신고
//	@PostMapping("/report/{boardNo}")
//	public String reportBoard(@PathVariable int boardNo, HttpSession session) {
//		// 세션에서 로그인된 사용자 정보를 가져옴
//		UserDto loginUser = (UserDto) session.getAttribute("login");
//
//		// 로그인된 사용자가 있을 경우에만 신고 가능
//		if (loginUser != null) {
//			// 해당 게시글에 대한 신고 여부 확인
//			if (!communityBiz.isReported(loginUser.getUser_no(), boardNo)) {
//				// 게시글 신고 카운트 증가
//				communityBiz.incrementReportCount(boardNo);
//
//				// COMMUNITY_REPORT에 신고 정보 저장
//				CommunityReportDto reportDto = new CommunityReportDto();
//				reportDto.setUserNo(loginUser.getUser_no());
//				reportDto.setBoardNo(boardNo);
//				communityBiz.reportCommunity(reportDto);
//
//				// 신고 후에는 상세 페이지로 리다이렉트
//				return "redirect:/community/detail/" + boardNo;
//			}
//		}
//
//		// 로그인되지 않았거나 이미 신고한 경우, 리스트 페이지로 리다이렉트
//		return "redirect:/nav/community/";
//	}

	// 게시글 신고
	@PostMapping("/report/{boardNo}")
	public ResponseEntity<String> reportBoard(@PathVariable int boardNo, HttpSession session) {
		// 세션에서 로그인된 사용자 정보를 가져옴
		UserDto loginUser = (UserDto) session.getAttribute("login");

		// 로그인된 사용자가 있을 경우에만 신고 가능
		if (loginUser != null) {
			// 해당 게시글에 대한 신고 여부 확인
			if (!communityBiz.isReported(loginUser.getUser_no(), boardNo)) {
				// 게시글 신고 카운트 증가
				communityBiz.incrementReportCount(boardNo);

				// COMMUNITY_REPORT에 신고 정보 저장
				CommunityReportDto reportDto = new CommunityReportDto();
				reportDto.setUserNo(loginUser.getUser_no());
				reportDto.setBoardNo(boardNo);
				communityBiz.reportCommunity(reportDto);

				// 신고 성공 응답 반환
				return ResponseEntity.ok("신고가 완료되었습니다.");
			} else {
				// 이미 신고한 경우 중복 신고 응답 반환
				return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 신고하셨습니다.");
			}
		}
		// 로그인되지 않은 경우 실패 응답
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	// 댓글 신고
	@PostMapping("/report/comment/{commentNo}")
	public String reportComment(@PathVariable int commentNo, HttpSession session) {
		// 세션에서 로그인된 사용자 정보를 가져옴
		UserDto loginUser = (UserDto) session.getAttribute("login");

		// 로그인된 사용자가 있을 경우에만 신고 가능
		if (loginUser != null) {
			// 해당 댓글에 대한 신고 여부 확인
			if (!communityBiz.isCommentReported(loginUser.getUser_no(), commentNo)) {
				// 댓글 신고 카운트 증가
				communityBiz.incrementCommentReportCount(commentNo);

				// CommentReportDto에 신고 정보 저장
				CommentReportDto reportDto = new CommentReportDto();
				reportDto.setUserNo(loginUser.getUser_no());
				reportDto.setCommentNo(commentNo);
				communityBiz.reportComment(reportDto);

				// 신고 후에는 게시글 상세 페이지로 리다이렉트
				CommentDto comment = communityBiz.getComment(commentNo);
				return "redirect:/community/detail/" + comment.getBoardNo();
			}
		}

		// 로그인되지 않았거나 이미 신고한 경우, 리스트 페이지로 리다이렉트
		return "redirect:/nav/community/";
	}

}
