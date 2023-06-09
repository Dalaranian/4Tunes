package com.multi.fourtunes.model.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.fourtunes.model.biz.KakaoPayBiz;
import com.multi.fourtunes.model.biz.KakaoPayBizImpl;
import com.multi.fourtunes.model.dto.ApproveDto;
import com.multi.fourtunes.model.dto.PayDto;
import com.multi.fourtunes.model.dto.ReadyDto;
import com.multi.fourtunes.model.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/jq")
public class KakaopayController {
	
	@Autowired
	private KakaoPayBiz kakaoPayBiz;

	// 결제 준비
	@RequestMapping("/kakaopay")
	public @ResponseBody ReadyDto payReady(PayDto payDto, HttpSession session, Model model) {
		ReadyDto readyDto = kakaoPayBiz.payReady();
		
		// log.debug("tid : " + readyDto.getTid());
		// log.debug("url : " + readyDto.getNext_redirect_pc_url());
		session.setAttribute("tid", readyDto.getTid());
		
		return readyDto;
	}
	
	// 결제 승인 요청
	@RequestMapping("/success")
	public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, HttpSession session, Model model) {
		// log.debug("pg_token : " + pg_token);
		String tid = (String)session.getAttribute("tid");
		// log.debug(tid);
		
		ApproveDto approveDto = kakaoPayBiz.payApprove(tid, pg_token);
		model.addAttribute("info", approveDto);
		
		// PAY 테이블에 결제정보 저장
//		int userNo = ((UserDto)session.getAttribute("login")).getUser_no();
		UserDto current = (UserDto) session.getAttribute("login");
		Date payDate = approveDto.getApproved_at();
		String payPrice = approveDto.getAmount().getTotal() + "";
		
		PayDto insert = new PayDto();
		insert.setUser_no(current.getUser_no());
		insert.setPay_date(payDate);
		insert.setPay_price(payPrice);
		
		if(kakaoPayBiz.insertPayInfo(insert) > 0) {
			// USER 테이블에 USER_GRADE update
			if(kakaoPayBiz.updateUserGrade(current.getUser_no()) > 0) {
				// log.debug("UserGrade update 성공");
				session.removeAttribute("login");
				current.setUser_grade("PAID");
				session.setAttribute("login", current);
				return "redirect:/nav/mypage";
			}
		}
		// 실패시 멤버십 안내 페이지로
		return "membership_join";
		
	}
	
}
