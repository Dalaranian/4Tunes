package com.multi.fourtunes.model.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.multi.fourtunes.model.biz.KakaoPayBizImpl;
import com.multi.fourtunes.model.dto.ApproveDto;
import com.multi.fourtunes.model.dto.PayDto;
import com.multi.fourtunes.model.dto.ReadyDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/jq")
public class KakaopayController {
	
	@Autowired
	private KakaoPayBizImpl kakaoPayBizImpl;

	// 결제 준비
	@RequestMapping("/kakaopay")
	public @ResponseBody ReadyDto payReady(PayDto payDto, HttpSession session, Model model) {
		ReadyDto readyDto = kakaoPayBizImpl.payReady();
		
		log.info("tid : " + readyDto.getTid());
		log.info("url : " + readyDto.getNext_redirect_pc_url());
		session.setAttribute("tid", readyDto.getTid());
		
		return readyDto;
	}
	
	// 결제 승인 요청
	@RequestMapping("/success")
	public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, HttpSession session, Model model) {
		log.info("pg_token : " + pg_token);
		String tid = (String)session.getAttribute("tid");
		System.out.println(tid);
		
		ApproveDto approveDto = kakaoPayBizImpl.payApprove(tid, pg_token);
		
		model.addAttribute("info", approveDto);
		
		return "payTestPage";
	}
	
	
	
	/*@ResponseBody
	public String kakaopay() {
		try {
			URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "KakaoAK 9c1e9cb55d9c01d51c85d999153133a2");
			con.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			con.setDoOutput(true); // Input은 default가 true임

			String param = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=4TUNES_MEMBERSHIP&quantity=1&total_amount=990&tax_free_amount=0&approval_url=http://localhost:8787/jq/success&cancel_url=http://localhost:8787/nav/membership&fail_url=http://localhost:8787/nav/membership";

			OutputStream output = con.getOutputStream();
			DataOutputStream dataoutput = new DataOutputStream(output);
			dataoutput.writeBytes(param); // byte단위로 자동 형변환
			dataoutput.flush(); // 가지고있는 데이터를 보냄
			dataoutput.close();

			int res = con.getResponseCode();

			InputStream input;
			if (res == 200) { // 성공한 경우
				input = con.getInputStream();
			} else { // 실패한 경우
				input = con.getErrorStream();
			}
			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader buffer = new BufferedReader(reader); // String으로 형변환 해줌

			return buffer.readLine();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{\"result\":\"NO\"}";
	}*/
	
	
}
