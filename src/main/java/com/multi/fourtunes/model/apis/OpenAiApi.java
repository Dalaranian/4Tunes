package com.multi.fourtunes.model.apis;

import com.multi.fourtunes.model.dto.SongDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Component
public class OpenAiApi {

    private int countKeyword;

    @Value("${openAiKey}")
    private String apiKey;

    RestTemplate restTemplate;
    final String url = "https://api.openai.com/v1/chat/completions";

    JSONObject requestBodyContent;


    public ArrayList<SongDto> suggestedSong(String[] keyword){

        restTemplate = new RestTemplate();

        // 통신하기 위해, RestTamplate 에 담을 header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        

        return null;
    }

    /**
     * RequestBody 에 담을 Json 생성하기
     * @param keyword
     */
    public void setMessage(String keyword[]){
        // 키워드외 갯수를 확인
        countKeyword = keyword.length;

        // JSON 루트 노드 생성
        JSONObject rootNode = new JSONObject();

        // JSON 그 다음 노드 생성
        JSONObject messages = new JSONObject();
        JSONObject functions = new JSONObject();

        rootNode.append("model", "gpt-3.5-turbo-0613");
        messages.append("role", "USER");
        messages.append("content", getContentByKeyword(keyword));

//        functions.append();

        rootNode.append("messages", messages);


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
        content.append(" includes title, artist");

        return content.toString();
    }

}
