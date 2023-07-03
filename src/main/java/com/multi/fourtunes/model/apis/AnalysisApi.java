package com.multi.fourtunes.model.apis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.multi.fourtunes.model.dao.KeywordDao;

@Component
public class AnalysisApi {
	@Autowired
	private KeywordDao keywordDao;

    // application.properties에 저장되어있는 APIKey
    @Value("${openAiKey}")
    private String apiKey;

    // Open AI API와 통신할 RestTemplate 선언
    RestTemplate restTemplate;
    final String url = "https://api.openai.com/v1/chat/completions";

    public List<Double> getKeywordRatios(String[] playlistSongsAndArtists) throws JsonMappingException, JsonProcessingException {
        List<Double> ratios = new ArrayList<>();

        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String[] keywords = keywordDao.getAllList();
        String content = getContentByPlaylistSongs(playlistSongsAndArtists, keywords);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", Arrays.asList(Map.of("role", "user", "content", content)));
        requestBody.put("model", "gpt-3.5-turbo-0613");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println("API Response: " + responseEntity.getBody()); // API 응답을 콘솔에 출력

//                JSONArray keywordAndRatios = parseKeywordAndRatios(responseEntity);
//                for (int i = 0; i < keywordAndRatios.length(); i++) {
//                    JSONObject keywordAndRatio = keywordAndRatios.getJSONObject(i);
//                    double ratio = keywordAndRatio.getDouble("ratio");
//                    ratios.add(ratio);
//                }
            } else {
                System.err.println("Failed to make the request. Status code: " + responseEntity.getStatusCodeValue());
            }
        } catch (HttpClientErrorException ex) {
            System.err.println("Error: " + ex.getMessage());
        }

        return ratios;
    }

    private String getContentByPlaylistSongs(String[] playlistSongsAndArtists, String[] keywords) {
        StringBuilder content = new StringBuilder();
        
        content.append(" ");

        // 플레이리스트에 포함된 노래들을 질문에 포함
        for (String song : playlistSongsAndArtists) {
            content.append(song).append(", ");
        }
        content.delete(content.length() - 2, content.length());

        
        content.append(" Among the given list of songs, ");
        
        content.append(" <keywords: ");
      
        for (String keyword : keywords) {
        	content.append(keyword).append(" ");
        }
        content.append(">  determine the top 3 keywords that are closest to the genres of these songs from the provided list of keywords. Show the top 3 keywords and their corresponding ratios. Please ensure that the sum of the ratios for the top 3 keywords amounts to 100. Please provide the response in JSON format.");
        // 중에서 어떤 keyword에 가까운지 상위 3개의 keyword와 ratio만 보여줘. ratio는 상위 3개의 keyword가 합쳐서 100%가 되야해. JSON 형식으로 줘
        System.out.println(content.toString());
        return content.toString();
    }

//    private JSONArray parseKeywordAndRatios(ResponseEntity<String> responseEntity) {
//        try {
//            JSONObject responseObject = new JSONObject(responseEntity.getBody());
//            JSONArray choices = responseObject.getJSONArray("choices");
//            JSONObject choice = choices.getJSONObject(0);
//            JSONObject output = choice.getJSONObject("message").getJSONObject("output");
//            JSONArray keywordAndRatios = output.getJSONArray("keywords_and_ratios");
//            return keywordAndRatios;
//        } catch (JSONException e) {
//            System.err.println("Failed to parse the response: " + e.getMessage());
//            return new JSONArray();
//        }
//    }
}
		
//	public List<Double> parseKeywordAndRatios(ResponseEntity<String> responseEntity) {
//    JSONArray keywordAndRatios = new JSONArray();
//
//    try {
//        // responseBody에서 키워드와 비율 정보를 추출
//        JSONObject responseJson = new JSONObject(responseEntity);
//        JSONObject choices = responseJson.getJSONArray("choices").getJSONObject(0);
//        String reply = choices.getString("message").split(",")[0];
//        JSONObject keywordAndRatioJson = new JSONObject(reply);
//
//        Iterator<String> keys = keywordAndRatioJson.keys();
//        List<Double> ratios = new ArrayList<>();
//        while (keys.hasNext()) {
//            String keyword = keys.next();
//            double ratio = keywordAndRatioJson.getDouble(keyword);
//            ratios.add(ratio);
//        }
//
//        return ratios;
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//
//    return Collections.emptyList();
//}

