package com.multi.fourtunes.model.apis;

import com.multi.fourtunes.model.dto.SongDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class OpenAiApi {

    // application.properties에 저장되어있는 APIKey
    @Value("${openAiKey}")
    private String apiKey;

    // Open AI API와 통신할 RestTemplate 선언
    RestTemplate restTemplate;
    final String url = "https://api.openai.com/v1/chat/completions";

    public ArrayList<SongDto> suggestedSong(String[] keyword){

        restTemplate = new RestTemplate();

        // 통신하기 위해, RestTamplate 에 담을 header 생성
        HttpHeaders headers = new HttpHeaders();
        
        // BearerAuth 인증방식으로, OpenAI에 계정 인증
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body에 넣을 JSON 준비
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("role", "user");
        
        String message = getContentByKeyword(keyword);
        messageMap.put("content", message);
        
        Map<String, Object> requestBody = new HashMap<>();

        // 위에서 만든 messageMap 을 리퀘스트바디에 넣음
        // Arrays.asList() -> 배열을 리스트로 만들어주는 애
        requestBody.put("messages", Arrays.asList(messageMap));
        System.out.println("리스트로 바꾸면: " + Arrays.asList(messageMap).toString());

        // Open Ai 자연어 처리 모델을 지정
        // gpt-3.5 turbo 가 가장 가성비 좋음
        requestBody.put("model", "gpt-3.5-turbo-0613");

        // 위에서 만들어놓은 Http Request에 들어갈 내용을 HttpRequest 에 담음
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {

            // 아까 만들어놓은 HttpRequest를 RestTamlate 에 담아 OpenAi API와 통신하고, 결과값을 responseEntity에 저장
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // 통신에 성공하면?
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                // Body 의 내용을 출력함
                System.out.println("Response: " + responseBody);
            } else {
                // 통신에 실패시, 실패 코드를 출력
                System.err.println("Failed to make the request. Status code: " + responseEntity.getStatusCodeValue());
            }
        } catch (HttpClientErrorException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        
		return null;
    }

    /**
     * OpenAi 에 보낼 질문(Content) 를 생성하는 method
     * @param keyword
     * @return
     */
    private String getContentByKeyword(String[] keyword) {
        StringBuffer content = new StringBuffer();

        content.append("Please recommend 5 songs about ");
        for(String str:keyword){
            content.append(str + ",");
        }
        content.append(" and return to JSON including only title and artist.");
        // 한글 버전 : ㅇㅇ에 관한 노래 5곡을 추천하고, title과 artist만 포함한 JSON으로 반환해줘. 단, 한국 노래는 음원제목 그대로 추천해줘.
        // 영어 버전 : Please recommend 5 songs about KPOP,여자가수,아이돌, and return to JSON including only korean_title and artist.
        return content.toString();
    }

}
