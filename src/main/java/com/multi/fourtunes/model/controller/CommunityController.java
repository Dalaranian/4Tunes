package com.multi.fourtunes.model.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.CommunityBiz;
import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;
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

	@RequestMapping("/create")
	public String communityCreate() {

		return "community_detail";
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
	
	
//	@PostMapping("/comment/delete/{boardNo}/{userNo}")
//    public String deleteComment(@PathVariable int boardNo, @PathVariable int userNo, HttpSession session) {
//        UserDto loginUser = (UserDto) session.getAttribute("login");
//        
//        // 로그인된 사용자와 댓글 작성자의 USER_NO가 같을 경우에만 삭제 가능
//        if (loginUser != null && loginUser.getUser_no() == userNo) {
//            communityBiz.deleteComment(boardNo, userNo);
//        }
//        
//        // 상세 페이지로 리다이렉트
//        return "redirect:/community/detail/" + boardNo;
//    }
	
	
	@PostMapping("/comment/delete/{boardNo}/{userNo}")
	public ResponseEntity<String> deleteComment(@PathVariable int boardNo, @PathVariable int userNo, HttpSession session) {
	    UserDto loginUser = (UserDto) session.getAttribute("login");
	    
	    // 로그인된 사용자와 댓글 작성자의 USER_NO가 같을 경우에만 삭제 가능
	    if (loginUser != null && loginUser.getUser_no() == userNo) {
	        communityBiz.deleteComment(boardNo, userNo);
	        return ResponseEntity.ok("댓글이 삭제되었습니다.");
	    }
	    
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("댓글 삭제 권한이 없습니다.");
	}

}
