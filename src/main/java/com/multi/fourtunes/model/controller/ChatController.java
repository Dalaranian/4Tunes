package com.multi.fourtunes.model.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RestController
public class ChatController {

	// 카카오톡 오픈빌더로 리턴할 스킬 API
	@RequestMapping(value = "/kkoChat/v1", method = { RequestMethod.POST, RequestMethod.GET }, headers = {
			"Accept=application/json" })
	public HashMap<String, Object> kkov3(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {

		HashMap<String, Object> resultJson = new HashMap<>();

		try {

			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(params);
			System.out.println(jsonInString);

			// 발화 들어온거 확인하기 
			// messageText = "발화내용" 
			HashMap<String, Object> userRequest = (HashMap<String, Object>) params.get("userRequest");
			// utterance 키에 해당하는 값을 추출하여 utter에 저장
			// replace("\n", "") 발화 내용에서 개행 문자를 제거
			String utter = userRequest.get("utterance").toString().replace("\n", "");

			// 응답으로 전달할 메시지들을 담는 List
			List<HashMap<String, Object>> outputs = new ArrayList<>();
			// 응답 메시지의 구조를 담는 Map
			HashMap<String, Object> template = new HashMap<>();
			// 텍스트 메시지를 담는 Map
			HashMap<String, Object> simpleText = new HashMap<>();
			// 실제 텍스트 내용을 담는 Map
			HashMap<String, Object> text = new HashMap<>();

			// quickReplies : 사용자에게 표시될 빠른 답변 옵션들을 담는 리스트
			List<HashMap<String, Object>> quickReplies = new ArrayList<>();
			HashMap<String, Object> quickRepl = new HashMap<>();
			quickRepl.put("action", "message");
			quickRepl.put("label", "음악검색");
			quickRepl.put("messageText", "음악검색");
			quickReplies.add(quickRepl);

			HashMap<String, Object> quickRepl2 = new HashMap<>();
			quickRepl2.put("action", "message");
			quickRepl2.put("label", "맞춤추천");
			quickRepl2.put("messageText", "맞춤추천");
			quickReplies.add(quickRepl2);

			HashMap<String, Object> quickRepl3 = new HashMap<>();
			quickRepl3.put("action", "message");
			quickRepl3.put("label", "멤버십");
			quickRepl3.put("messageText", "멤버십");
			quickReplies.add(quickRepl3);

			HashMap<String, Object> quickRepl4 = new HashMap<>();
			quickRepl4.put("action", "message");
			quickRepl4.put("label", "회원탈퇴");
			quickRepl4.put("messageText", "회원탈퇴");
			quickReplies.add(quickRepl4);

			text.put("text", "4Tunes 메뉴 리스트를 소개해드립니다.");
			simpleText.put("simpleText", text);
			outputs.add(simpleText);

			template.put("outputs", outputs);
			template.put("quickReplies", quickReplies);

			resultJson.put("version", "2.0");
			resultJson.put("template", template);

		} catch (Exception e) {

		}

		return resultJson;
	}
	
}
