package com.multi.fourtunes.model.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/jq")
public class KakaopayController {
	
	@RequestMapping("/kakaopay")
	@ResponseBody
	public String kakaopay() {
		try {
			URL url = new URL("https://kapi.kakao.com/v1/payment/ready");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "KakaoAK 9c1e9cb55d9c01d51c85d999153133a2");
			con.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			con.setDoOutput(true); //Input은 default가 true임
			
			String param = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=4TUNES_MEMBERSHIP&quantity=1&total_amount=990&tax_free_amount=0&approval_url=http://localhost:8787/nav/mypage&cancel_url=http://localhost:8787/nav/membership&fail_url=http://localhost:8787/nav/membership";
		
			OutputStream output = con.getOutputStream();
			DataOutputStream dataoutput = new DataOutputStream(output);
			dataoutput.writeBytes(param); //byte단위로 자동 형변환
			dataoutput.flush(); //가지고있는 데이터를 보냄
			dataoutput.close();
			
			int res = con.getResponseCode();
			
			InputStream input;
			if(res == 200) { //성공한 경우
				input = con.getInputStream();
			} else { //실패한 경우
				input = con.getErrorStream();
			}
			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader buffer = new BufferedReader(reader); //String으로 형변환 해줌 
			
			return buffer.readLine();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{\"result\":\"NO\"}";
		
	}
}
