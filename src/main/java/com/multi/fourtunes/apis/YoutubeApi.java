package com.multi.fourtunes.apis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class YoutubeApi {
	
	/**
	 * @param singer	(가수 이름)
	 * @param title		(노래 제목)
	 * @return EmebedLink by String
	 */
	
	private RestTemplate restTemplate;
	
	private final String apiUrl = "https://www.googleapis.com/youtube/v3/search";
	
	// application.properties 에 저장되어있는 값 불러오기
	@Value("${youtubekey}")
	private String apiKey;
	
	
	public String embedLinkGetter(String singer, String title) {
		
		restTemplate = new RestTemplate();
		
		// UriComponentsBuilder를 사용하여 URL 및 쿼리 파라미터를 생성
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("part", "snippet")
                .queryParam("q", "아이유_밤편지")
                .queryParam("key", apiKey)
                .build();		
        
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, String.class);
        
        System.out.println(responseEntity.getBody());
        
		return "";
	}

}
