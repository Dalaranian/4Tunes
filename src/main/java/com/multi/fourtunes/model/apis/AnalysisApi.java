package com.multi.fourtunes.model.apis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

@Component
public class AnalysisApi {

	// application.properties에 저장되어있는 APIKey
	@Value("${openAiKey}")
	private String apiKey;

	// Open AI API와 통신할 RestTemplate 선언
	RestTemplate restTemplate;
	final String url = "https://api.openai.com/v1/chat/completions";

	public List<Double> getKeywordRatios(String[] playlistSongsAndArtists)
			throws JsonMappingException, JsonProcessingException {
		List<Double> ratios = new ArrayList<>();

		restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(apiKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 플레이리스트 노래들을 질문에 포함
		String content = getContentByPlaylistSongs(playlistSongsAndArtists);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("messages", Arrays.asList(Map.of("role", "user", "content", content)));
		requestBody.put("model", "gpt-3.5-turbo-0613");

		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					String.class);

			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				JSONArray keywordAndRatios = parseKeywordAndRatios(responseEntity);
				for (int i = 0; i < keywordAndRatios.length(); i++) {
					JSONObject keywordAndRatio = keywordAndRatios.getJSONObject(i);
					double ratio = keywordAndRatio.getDouble("ratio");
					ratios.add(ratio);
				}
			} else {
				System.err.println("Failed to make the request. Status code: " + responseEntity.getStatusCodeValue());
			}
		} catch (HttpClientErrorException ex) {
			System.err.println("Error: " + ex.getMessage());
		}

		return ratios;
	}

	public JSONArray parseKeywordAndRatios(ResponseEntity<String> responseEntity) {
		JSONArray keywordAndRatios = new JSONArray();

		try {
			// ResponseEntity의 Body 추출
			JSONObject responseBody = new JSONObject(responseEntity.getBody());

			// responseBody에서 키워드와 비율 정보를 추출
			JSONArray keys = responseBody.names();
			for (int i = 0; i < keys.length(); i++) {
				String keyword = keys.getString(i);
				double ratio = responseBody.getDouble(keyword);

				// 키워드와 비율을 JSONObject로 조합하여 배열에 추가
				JSONObject keywordAndRatio = new JSONObject();
				keywordAndRatio.put("keyword", keyword);
				keywordAndRatio.put("ratio", ratio);
				keywordAndRatios.put(keywordAndRatio);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return keywordAndRatios;
	}

	/**
	 * OpenAI에 보낼 질문(Content)을 생성하는 메서드
	 * 
	 * @param playlistSongsAndArtists 사용자의 플레이리스트 노래 목록
	 * @return 생성된 질문
	 */
	private String getContentByPlaylistSongs(String[] playlistSongsAndArtists) {
		StringBuffer content = new StringBuffer();

		// 플레이리스트에 포함된 노래들을 질문에 포함
		for (String song : playlistSongsAndArtists) {
			content.append(song).append(", ");
		}
		content.delete(content.length() - 2, content.length());

		content.append("이 노래들이 키워드 38개 중에 어떤 것에 가까운지 비율 100% 기준으로 상위 3개의 키워드를 알려줘. JSON으로 반환해줘.");

		return content.toString();
	}

}
