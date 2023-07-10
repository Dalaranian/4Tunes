package com.multi.fourtunes.model.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.fourtunes.model.dto.SongDto;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class OpenAiApi {

    // application.properties에 저장되어있는 APIKey
    @Value("${openAiKey}")
    private String apiKey;

    // Open AI API와 통신할 RestTemplate 선언
    RestTemplate restTemplate;
    final String url = "https://api.openai.com/v1/chat/completions";

    public ArrayList<SongDto> suggestedSong(String keyword){
    	
    	// 추천결과 10곡을 담을 result 변수 선언
    	ArrayList<SongDto> result = new ArrayList<>();

        restTemplate = new RestTemplate();

        // 통신하기 위해, RestTamplate 에 담을 header 생성
        HttpHeaders headers = new HttpHeaders();
        
        // BearerAuth 인증방식으로, OpenAI에 계정 인증
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // requestBody에 넣을 JSON 준비
        // messagesMap : role + content을 포함
        // -> role: GPT의 역할을 지정(system/assistant/user)
        // -> content: GPT에게 던질 질문
        Map<String, Object> messagesMap = new HashMap<>();
        
        // GPT의 역할을 user로 지정
        messagesMap.put("role", "user");
        
        // 매개변수로 받아온 keyword 배열을 토대로, GPT에게 던질 질문 생성하여 content에 저장
        String message = getContentByKeyword(keyword);
        messagesMap.put("content", message);
        
        Map<String, Object> requestBody = new HashMap<>();

        // 위에서 만든 messageMap 을 requestBody에 넣음
        // Arrays.asList() -> 배열을 리스트로 만들어주는 역할
        requestBody.put("messages", Arrays.asList(messagesMap));
        log.info("messages : " + Arrays.asList(messagesMap).toString());

        // OpenAI 자연어 처리 모델을 지정
        // gpt-3.5-turbo 가 가장 가성비 좋음
        requestBody.put("model", "gpt-3.5-turbo-0613");

        // 위에서 만들어놓은 Http Request(HttpHeaders, requestBody)를 HttpEntity 에 담음
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // 요청 내용을 담은 HttpEntity(requestEntity)를 RestTamlate에 담아 OpenAI API와 통신하고, 결과값을 responseEntity에 저장
        	// -> exchange(요청 url, 요청방식 지정, 요청내용, 응답내용 타입 지정)
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // OpenAI API와 통신 성공한 경우
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
            	
            	// 응답받은 내용(responseEntity) 파싱 작업
            	// 파싱 완료되면, 제목과 가수만 포함한 노래 10곡의 JSON 배열이 반환된다.
                JSONArray songsArray = parsingJson(responseEntity);
                
                try {
					// 노래 한곡씩 SongDto에 담아, ArrayList(result)에 10곡 모두 넣어줌
					for(int i=0; i<=songsArray.length()-1; i++) {
						// ArrayList<SongDto> result에 10곡의 정보를 담기위한 SongDto 선언
						SongDto current = new SongDto();
						
						JSONObject oneSong = songsArray.getJSONObject(i);
						//getString("") : ""키의 값을 리턴
						current.setSongArtist(oneSong.getString("artist"));
						current.setSongTitle(oneSong.getString("title"));
						
						// result에는 10곡의 SongDto가 모두 담겨짐
						result.add(current);
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
                
            } else {  // OpenAI API와 통신 실패한 경우
                System.err.println("Failed to make the request. Status code: " + responseEntity.getStatusCodeValue());
            }
            
        } catch (HttpClientErrorException ex) {
            System.err.println("Error: " + ex.getMessage());
        } catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch(HttpServerErrorException e) {
			e.printStackTrace();
		}
        
		return result;
    }

	private JSONArray parsingJson(ResponseEntity<String> responseEntity)
			throws JSONException, JsonProcessingException, JsonMappingException {
		
		// getJSONArray("songs")를 통해 키 songs의 JSON 배열 추출
		JSONArray songsArray;
		try {
			// ResponseEntity의 Body 추출
			JSONObject responseBody = new JSONObject(responseEntity.getBody());
			log.info("응답: " + responseBody);
			
			// responseBody에서 노래 제목과 가수만 추출하는 파싱 작업
			// ObjectMapper : JSON을 JAVA객체로 역직렬화하는 Jackson의 클래스
			ObjectMapper objMapper = new ObjectMapper();
			
			// readTree() : JSON 문자열을 JsonNode로 변환
			JsonNode rootNode = objMapper.readTree(responseBody.toString());
			JsonNode choicesNode = rootNode.get("choices");
			JsonNode messageNode = choicesNode.get(0);
			JsonNode messageNodeRes = messageNode.get("message");
			JsonNode contentNode = messageNodeRes.get("content");

			// content에 포함되어있는 \n, \, 공백(" ") 삭제
			String contentString = contentNode.toString().replace("\\n", "").replace("\\", "").replace(" ", "");
			
			// 맨앞 큰따옴표(") 삭제
			// trim(): 앞뒤 공백 삭제 / split(a, b): a를 기준으로 b개로 쪼개기
			String[] str = contentString.trim().split("\"", 2);
			
			JSONObject songs = new JSONObject(str[1]);
			
			songsArray = songs.getJSONArray("songs");
		} catch (JsonMappingException e) {
			songsArray = null;
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			songsArray = null;
			e.printStackTrace();
		} catch (JSONException e) {
			songsArray = null;
			e.printStackTrace();
		}
		
		return songsArray;
	}

    /**
     * OpenAi 에 보낼 질문(Content) 를 생성하는 method
     * @param keyword
     * @return
     */
    private String getContentByKeyword(String keyword) {
        StringBuffer content = new StringBuffer();
        
        content.append(keyword);
        //content.append("Please recommend 5 songs about ");
        content.append("에 관한 노래 10곡을 추천하고, title과 artist만 포함한 JSON으로 반환해줘.");
        // 한글 버전 : ㅇㅇ에 관한 노래 5곡을 추천하고, title과 artist만 포함한 JSON으로 반환해줘. 단, 한국 노래는 음원제목 그대로 추천해줘.
        // 영어 버전 : Please recommend 5 songs about KPOP,여자가수,아이돌, and return to JSON including only korean_title and artist.
        return content.toString();
    }

}
