package com.multi.fourtunes.apis;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.multi.fourtunes.model.dto.SongDto;

public class ManiaDbApi {

	// ManiaDB API URL 예시
	// https://www.maniadb.com/api/search/metallica/?sr=artist&display=10&key=example&v=0.5
	final static String URL_PREFIX = "https://www.maniadb.com/api/search/";
	// 여기 사이에 검색어가 들어가고
	final static String URL_INTERFIX = "/?sr=";
	// 여기 사이에 검색 타입(artist, song) 이 들어감
	final static String URL_SUFFIX = "&display=10&key=example&v=0.5";

	// 검색어를 저장하는 변수
	private String searchPrompt;
	// 검색 타입을 저장하는 변수
	private String searchType;

	private RestTemplate restTemplate;

	public ManiaDbApi() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 검색어 설정
	public void setPrompt(String prompt) {
		this.searchPrompt = prompt;
	}

	// 검색 타입 설정
	public void setType(boolean type) {
		// type 이 true일 경우, artist
		// false 인 경우, song
	    searchType = type ? "artist" : "song";
	}
	
	public ArrayList<SongDto> search() {
		if(searchPrompt !=null&&searchType != null) {
			
			// set 된 데이터를 포장하여 REST URL 형식으로 변환
			String restURL = MakeURL();
			System.out.println("요청 URL : " + restURL);
			
			// ManiaDB 에 API 요청 후 값 받기
			restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(restURL, HttpMethod.GET, null, String.class);
			String responseBodyByXML = response.getBody();
			
			// 받은 값을 파싱하기
			return parseResonsBody(responseBodyByXML);
		}else {
			return null;
		}
	}

	private ArrayList<SongDto> parseResonsBody(String responseBodyByXML) {
		// TODO Auto-generated method stub
		
		// XML 을 JSON 으로 파싱
		JSONObject jsonObject = XML.toJSONObject(responseBodyByXML);
		
		System.out.println(jsonObject.toString());
		
		 // TODO: JSON을 SongDto 목록으로 변환하는 로직을 구현해야 함
		return null;
	}

	// API 요쳥 형식대로 URL 을 만들어줌
	private String MakeURL() {
		// TODO Auto-generated method stub
		StringBuffer urlBuffer = new StringBuffer();
		urlBuffer.append(URL_PREFIX);
		// 검색어 넣기
		urlBuffer.append(searchPrompt);
		urlBuffer.append(URL_INTERFIX);
		// 검색 타입 넣기
		urlBuffer.append(searchType);
		urlBuffer.append(URL_SUFFIX);
		return urlBuffer.toString();
	}
}
