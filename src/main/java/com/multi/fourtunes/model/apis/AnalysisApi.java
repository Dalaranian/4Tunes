package com.multi.fourtunes.model.apis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.multi.fourtunes.model.biz.ChartBizImpl;
import com.multi.fourtunes.model.biz.PlaylistBizImple;
import com.multi.fourtunes.model.dao.KeywordDao;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import com.multi.fourtunes.model.mapper.PlayListMapper;

@Component
public class AnalysisApi {
	@Autowired
	private KeywordDao keywordDao;
	
	@Autowired
	ChartBizImpl playlistBizimpl;
	
	// application.properties에 저장되어있는 APIKey
	@Value("${openAiKey}")
	private String apiKey;

	// Open AI API와 통신할 RestTemplate 선언
	RestTemplate restTemplate;
	final String url = "https://api.openai.com/v1/chat/completions";

	/**
	 * OpenAI API를 사용하여 주어진 노래 정보를 기반으로 키워드를 추출
	 *
	 * @param songInfo 노래 정보 배열
	 * @return 추출된 키워드 리스트
	 * @throws JsonMappingException    JSON 매핑 예외
	 * @throws JsonProcessingException JSON 처리 예외
	 */
	public List<String> suggestedAIKeyword(String[] songInfo) throws JsonMappingException, JsonProcessingException {
		// 추천된 키워드를 담을 리스트 생성
		List<String> AIKeyword = new ArrayList<>();
		// RestTemplate 객체 생성
		restTemplate = new RestTemplate();
		// API 요청을 위한 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 모든 키워드 배열 가져오기
		String[] keywords = keywordDao.getAllList();
		// AI가 키워드를 추출할 content 생성
		String content = getAIKeyword(songInfo, keywords);

		// API 요청에 필요한 데이터를 생성
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("messages", Arrays.asList(Map.of("role", "user", "content", content)));
		requestBody.put("model", "gpt-3.5-turbo-0613");

		// API 요청을 보낼 HTTP 엔티티 생성
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

		try {
			// API 요청을 보내고 응답 받기
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			
			// API 응답이 성공적으로 수신된 경우
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				// API 응답을 콘솔에 출력
				System.out.println("API Response: " + responseEntity.getBody());

				// API 응답에서 키워드 추출
				String Finalkeyword = AIKeywordFromJson(responseEntity);
				// 추출된 키워드를 콘솔에 출력
				System.out.println("Keyword: " + Finalkeyword);
				// 추출된 키워드를 리스트에 추가
				AIKeyword.add(Finalkeyword);
					
			} else {
				System.err.println("Failed to make the request. Status code: " + responseEntity.getStatusCodeValue());
			}
		} catch (HttpClientErrorException ex) {
			System.err.println("Error: " + ex.getMessage());
		}
		// 추출된 키워드 리스트 반환
		return AIKeyword;

	}

	/**
	 * 노래 정보와 키워드 배열을 기반으로 API에 전달할 콘텐츠를 생성
	 *
	 * @param songInfo 노래 정보 배열
	 * @param keywords 키워드 배열
	 * @return 생성된 콘텐츠 문자열
	 */
	private String getAIKeyword(String[] songInfo, String[] keywords) {

		// StringBuilder content = new StringBuilder();
		// 마지막 ,를 따로 처리해주기 위한 StringJoiner
		StringJoiner content = new StringJoiner(" ");
		
		// 노래 정보 배열을 순회하면서 content에 추가
		for (String song : songInfo) {
			content.add("<" + song + ">");
		}
		content.add(", ");
		content.add("주어진 노래-가수가");
		content.add("<keyword: " + String.join(", ", keywords) + ">");
		content.add("중에서 어떤 keyword에 제일 유사한지 keyword 하나만 json 형식으로 반환해줘.");

		System.out.println(content.toString());
		// content 문자열 반환
		return content.toString();
	}

	/**
	 * API 응답에서 키워드를 추출
	 *
	 * @param responseEntity API 응답 엔티티
	 * @return 추출된 키워드 문자열
	 */
	private String AIKeywordFromJson(ResponseEntity<String> responseEntity) {
			// API 응답 문자열을 JSONObject로 반환
			JSONObject responseBody = new JSONObject(responseEntity.getBody());
			// "choices" 배열을 가져옴
			JSONArray choicesArray = responseBody.getJSONArray("choices");

			if (choicesArray.length() > 0) {
				// 첫 번째 choice 객체를 가져옴
				JSONObject choice = choicesArray.getJSONObject(0);
				// choice 객체에서 "message" 객체를 가져옴
				JSONObject message = choice.getJSONObject("message");
				// "content" 문자열을 가져옴
				String content = message.getString("content");
				// content 문자열을 JSONObject로 변환
				JSONObject contentObject = new JSONObject(content);
				// contentObject에서 "keyword" 문자열을 가져옴
				String FinalKeyword = contentObject.getString("keyword");

				// 추출된 키워드 반환
				return FinalKeyword;
			}
			// 추출된 키워드가 없는 경우 "가요" 기본값을 반환
			return "가요";
	}
}
