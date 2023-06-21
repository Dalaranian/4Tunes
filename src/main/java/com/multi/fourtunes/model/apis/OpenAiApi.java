package com.multi.fourtunes.model.apis;

import com.multi.fourtunes.model.dto.SongDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    public ArrayList<SongDto> suggestedSong(String[] keyword){

        restTemplate = new RestTemplate();

        // 통신하기 위해, RestTamplate 에 담을 header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(setBody(keyword).toString(), headers);

        System.out.println("entity is : " + entity.toString());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        System.out.println("response is : \n" + response.toString());

        return null;
    }

    /**
     * RequestBody 에 담을 Json 생성하기
     * @param keyword
     */
    private JSONObject setBody(String keyword[]){
        // 키워드외 갯수를 확인
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("model", "gpt-3.5-turbo-0613");

        JSONArray messagesArray = new JSONArray();
        JSONObject messageObject = new JSONObject();
        messageObject.put("role", "user");
        messageObject.put("content", "내 아내, 어머니 및 두 아들과 딸을 위해 본에서 암스테르담으로 여행을 예약해야 합니다. 저도 함께 갈 것입니다. 항공사는 직항편이어야 합니다.");
        messagesArray.put(messageObject);
        jsonObject.put("messages", messagesArray);

        JSONArray functionsArray = new JSONArray();
        JSONObject functionObject = new JSONObject();
        functionObject.put("name", "travel_reservation");
        functionObject.put("description", "여행 예약하기");

        JSONObject parametersObject = new JSONObject();
        parametersObject.put("type", "object");

        JSONObject propertiesObject = new JSONObject();

        JSONObject destinationObject = new JSONObject();
        destinationObject.put("type", "string");
        destinationObject.put("description", "여행 목적지");
        propertiesObject.put("destination", destinationObject);

        JSONObject departureObject = new JSONObject();
        departureObject.put("type", "string");
        departureObject.put("description", "출발지");
        propertiesObject.put("departure", departureObject);

        JSONObject numberPeopleObject = new JSONObject();
        numberPeopleObject.put("type", "string");
        numberPeopleObject.put("description", "여행하는 사람 수");
        propertiesObject.put("number_people", numberPeopleObject);

        JSONObject travelModeObject = new JSONObject();
        travelModeObject.put("type", "string");
        travelModeObject.put("description", "여행 모드");
        propertiesObject.put("travel_mode", travelModeObject);

        parametersObject.put("properties", propertiesObject);
        functionObject.put("parameters", parametersObject);

        functionsArray.put(functionObject);
        jsonObject.put("functions", functionsArray);

        System.out.println(jsonObject.toString());

        return jsonObject;
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
