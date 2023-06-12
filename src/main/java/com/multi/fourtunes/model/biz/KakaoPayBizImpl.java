package com.multi.fourtunes.model.biz;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.multi.fourtunes.model.dto.ApproveDto;
import com.multi.fourtunes.model.dto.ReadyDto;

@Service
public class KakaoPayBizImpl {
	
	// 결제 준비
	public ReadyDto payReady() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", "partner_order_id");
		params.add("partner_user_id", "partner_user_id");
		params.add("item_name", "4TUNES_MEMBERSHIP");
		params.add("quantity", "1");
		params.add("total_amount", "990");
		params.add("tax_free_amount", "0");
		params.add("approval_url", "http://localhost:8787/jq/success");
		params.add("cancel_url", "http://localhost:8787/nav/membership");
		params.add("fail_url", "http://localhost:8787/nav/membership");
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, this.getHeaders());
		
		RestTemplate template = new RestTemplate();
		
		String url = "https://kapi.kakao.com/v1/payment/ready";
		
		ReadyDto readyDto = template.postForObject(url, requestEntity, ReadyDto.class);
		
		return readyDto;
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK 9c1e9cb55d9c01d51c85d999153133a2");
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		return headers;
	}
	
	// 결제 승인 요청
	public ApproveDto payApprove(String tid, String pg_token) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("tid", tid);
		params.add("partner_order_id", "partner_order_id");
		params.add("partner_user_id", "partner_user_id");
		params.add("pg_token", pg_token);
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, this.getHeaders());
		
		RestTemplate template = new RestTemplate();
		
		String url = "https://kapi.kakao.com/v1/payment/approve";
		
		ApproveDto approveDto = template.postForObject(url, requestEntity, ApproveDto.class);
		
		return approveDto;
	}
}
