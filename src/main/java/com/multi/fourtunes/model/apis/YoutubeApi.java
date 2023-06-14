package com.multi.fourtunes.model.apis;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class YoutubeApi {

	private RestTemplate restTemplate;

	// 요청할 API 의 URL
	private final String apiUrl = "https://www.googleapis.com/youtube/v3/search";

	private final static String EMBED_LINK_PREFIX = "https://www.youtube.com/embed/";

	// application.properties 에 저장되어있는 값 불러오기
	@Value("${youtubeapi.key}")
	private String apiKey;

	/**
	 * @param singer (가수 이름)
	 * @param title  (노래 제목)
	 * @return EmebedLink by String
	 */
	public String embedLinkGetter(String singer, String title) {

		restTemplate = new RestTemplate();

		String query = urlBuilder(singer, title);

		// UriComponentsBuilder를 사용하여 URL 및 쿼리 파라미터를 생성
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(apiUrl).queryParam("part", "id,snippet")
				.queryParam("q", query).queryParam("key", apiKey).queryParam("type", "video").build();

		ResponseEntity<String> responseEntity = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null,
				String.class);

		JSONObject response = new JSONObject(responseEntity.getBody());
		ObjectMapper objMapper = new ObjectMapper();

		String id = jsonParser(response, objMapper);

		return EMBED_LINK_PREFIX + id;
	}

	/**
	 * @param response
	 * @param objMapper
	 * @return response 를 파싱해서, embedLink 를 반환
	 */
	private String jsonParser(JSONObject response, ObjectMapper objMapper) {
		JsonNode rootNode;
		try {
			rootNode = objMapper.readTree(response.toString());
//			System.out.println(rootNode.toPrettyString());
//			System.out.println("-------------------------------------");
			JsonNode itemsNode = rootNode.get("items");
//			System.out.println(itemsNode.toPrettyString());
//			System.out.println("-------------------------------------");
			JsonNode targetNode = itemsNode.get(0);
//			System.out.println(targetNode.toPrettyString());
//			System.out.println("-------------------------------------");
			JsonNode targetIdNode = targetNode.get("id");
//			System.out.println(targetIdNode.toPrettyString());
//			System.out.println("-------------------------------------");
			String id = targetIdNode.get("videoId").asText();

			return id;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "undefine";
	}

	/**
	 * @param singer
	 * @param title
	 * @return 위 파라미터로 조합된 URL
	 */
	private String urlBuilder(String singer, String title) {
		StringBuilder query = new StringBuilder();
		query.append(singer);
		query.append("-");
		query.append(title);
		return query.toString();
	}

}
