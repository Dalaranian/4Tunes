package com.multi.fourtunes.apis;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.tools.EmebedLinkGetter;

/**
 * @author Dalanarian
 *
 */
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

	/**
	 * 검색어를 설정하는 메소드 <br>
	 * String prompt : 검색어 (ex. 소녀시대 / 밤편지)
	 * 
	 * @param prompt type String
	 */
	public void setPrompt(String prompt) {
		this.searchPrompt = prompt;
	}

	/**
	 * 검색 타입을 설정하는메소드 <br>
	 * boolean type : 타입 지정 (true 일 시 artist, false 일 시 song)
	 * 
	 * @param type boolean
	 */
	public void setType(boolean type) {
		// type 이 true일 경우, artist
		// false 인 경우, song
		searchType = type ? "artist" : "song";
	}

	/**
	 * 검색된 Song 의 정보를 SongDto 에 담아 List 로 리턴
	 * 
	 * @return ArrayList<SongDto>
	 */
	public ArrayList<SongDto> search() {
		if (searchPrompt != null && searchType != null) {

			// set 된 데이터를 포장하여 REST URL 형식으로 변환
			String restURL = MakeURL();
			System.out.println("요청 URL : " + restURL);

			// ManiaDB 에 API 요청 후 값 받기
			restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(restURL, HttpMethod.GET, null, String.class);
			String responseBodyByXML = response.getBody();

			// 받은 값을 파싱하기
			return parseResponseBody(responseBodyByXML);
		} else {
			throw new IllegalArgumentException("검색어와 검색 타입이 잘 입력되었는지 확인해주세요. ");
		}
	}

	private ArrayList<SongDto> parseResponseBody(String responseBodyByXML) {
		// 결과를 저장할 리스트 선언
		ArrayList<SongDto> result = new ArrayList<>();

		// XML 을 JSON 으로 파싱
		JSONObject jsonObject = XML.toJSONObject(responseBodyByXML);

		System.out.println("불러온 JSON : " + jsonObject.toString());

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			// 원하는 데이터까지 탐색하기
			JsonNode rootNode = objectMapper.readTree(jsonObject.toString());
			JsonNode rssNode = rootNode.get("rss");
			JsonNode channelNode = rssNode.get("channel");
			JsonNode itemArrayNode = channelNode.get("item");

			// 데이터 꺼내서 DTO에 포장 후 List 에 넣
			for (JsonNode itemNode : itemArrayNode) {
				System.out.println(itemNode.toString());
				String title = itemNode.get("title").asText();
				String name = itemNode.get("maniadb:artist").get("name").asText();
				String link = itemNode.get("link").asText();

				System.out.println("Title: " + title);
				System.out.println("Name: " + name);
				System.out.println("Link " + link);
				
				EmebedLinkGetter elg = new EmebedLinkGetter();
				
				String embedLink = elg.getLink(title, link);
				
				// SongDto 생성후 값 Set
				SongDto currentMusic = new SongDto();
				currentMusic.setArtist(name);
				currentMusic.setTitle(title);
				
				currentMusic.toString();
				
				
				// 다 불러온 친구를 저장
				result.add(currentMusic);
				System.out.println("----------------");
				
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * API 요쳥 형식대로 URL 을 만들어줌
	 * 
	 * @return String
	 */
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
