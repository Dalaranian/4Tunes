package com.multi.fourtunes.apis;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class YoutubeApi {

	/**
	 * @param singer (가수 이름)
	 * @param title  (노래 제목)
	 * @return EmebedLink by String
	 */

	private RestTemplate restTemplate;

	// 요청할 API 의 URL
	private final String apiUrl = "https://www.googleapis.com/youtube/v3/search";
	
	private final static String EMBED_LINK_PREFIX = "https://www.youtube.com/embed/"; 

	// application.properties 에 저장되어있는 값 불러오기
	@Value("${youtubeapi.key}")
	private String apiKey;

	public String embedLinkGetter(String singer, String title) {

		restTemplate = new RestTemplate();
		
//		파라미터로 넘어온 단어들 조합하기
		StringBuilder query = new StringBuilder();
		query.append(singer);
		query.append("-");
		query.append(title);
		
		System.out.println(query.toString());

		// UriComponentsBuilder를 사용하여 URL 및 쿼리 파라미터를 생성
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(apiUrl).queryParam("part", "id,snippet")
				.queryParam("q", query.toString()).queryParam("key", apiKey).queryParam("type", "video").build();

		ResponseEntity<String> responseEntity = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null,
				String.class);

		JSONObject response = new JSONObject(responseEntity.getBody());

		ObjectMapper objMapper = new ObjectMapper();

		
		// Json 을 탐색해서 videoId를 id 에 저장함
		try {
			JsonNode rootNode = objMapper.readTree(response.toString());
//			System.out.println(rootNode.toPrettyString());
//			System.out.println("-------------------------------------");
			JsonNode itemsNode = rootNode.get("items");
//			System.out.println(itemsNode.toPrettyString());
//			System.out.println("-------------------------------------");
			JsonNode targetNode = itemsNode.get(0);
			System.out.println(targetNode.toPrettyString());
//			System.out.println("-------------------------------------");
			JsonNode targetIdNode = targetNode.get("id");
//			System.out.println(targetIdNode.toPrettyString());
//			System.out.println("-------------------------------------");
			String id = targetIdNode.get("videoId").asText();

			return EMBED_LINK_PREFIX + id;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			System.out.println(singer + " " + title + "노래는 뭔가 뭔가 이상함");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println(singer + " " + title + "노래는 뭔가 뭔가 이상함");
		}

		return "undefine";
	}

}
