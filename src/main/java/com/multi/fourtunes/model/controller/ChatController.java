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
	public HashMap<String, Object> kkov2(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {

		HashMap<String, Object> resultJson = new HashMap<>();

		try {

			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(params);
			System.out.println(jsonInString);

			/* 발화 들어온거 확인하기 * */
			HashMap<String, Object> userRequest = (HashMap<String, Object>) params.get("userRequest");
			String utter = userRequest.get("utterance").toString().replace("\n", "");

			List<HashMap<String, Object>> outputs = new ArrayList<>();
			HashMap<String, Object> template = new HashMap<>();
			HashMap<String, Object> simpleText = new HashMap<>();
			HashMap<String, Object> text = new HashMap<>();

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

			text.put("text", "4Tunes 메뉴 리스트입니다.");
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
