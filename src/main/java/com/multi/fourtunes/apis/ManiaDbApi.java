package com.multi.fourtunes.apis;

import org.springframework.web.client.RestTemplate;

public class ManiaDbApi {

	// ManiaDB API URL 예시
	// https://www.maniadb.com/api/search/metallica/?sr=artist&display=10&key=example&v=0.5
	final static String URL_PREFIX = "https://www.maniadb.com/api/search/";
	final static String URL_INTERFIX = "/?sr=";
	final static String URL_SERFIX = "&display=10&key=example&v=0.5";

	// 검색어를 저장하는 변수
	String searchPrompt;
	// 검색 타입을 저장하는 변수
	String searchType;

	RestTemplate restTemplate;

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
		if (type) {
			searchType = "artist";
		} else {
			searchType = "song";
		}
	}
}
