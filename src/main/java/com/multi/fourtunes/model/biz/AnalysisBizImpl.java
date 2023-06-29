package com.multi.fourtunes.model.biz;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class AnalysisBizImpl implements AnalysisBiz {

	 // application.properties에 저장되어있는 APIKey
    @Value("${openAiKey}")
    private String apiKey;

    // Open AI API와 통신할 RestTemplate 선언
    RestTemplate restTemplate;
    final String url = "https://api.openai.com/v1/chat/completions";


	private static final String[] GENRES = { "KPOP", "남자가수", "여자가수", "발라드", "댄스", "R&B", "가요", "OST", "EDM", "힙합",
			"트로트", "재즈", "동요", "국악", "아이돌", "CCM", "불교음악", "뉴에이지", "일렉트로닉", "레게", "디스코", "락", "펑크", "블루스", "라틴", "컨트리",
			"로큰롤", "팝송", "포크", "클래식", "인디", "혼성그룹", "메탈", "어쿠스틱", "하우스", "찬송가", "싱어송라이터" };

	@GetMapping("/")
	public String getPlaylistGenres(Model model) {
		// 현재 사용자의 플레이리스트 조회 로직
		String[] playlistKeywords = getPlaylistKeywords(/* 플레이리스트 노래 목록을 전달 */);

		// OpenAI에 키워드를 입력하여 상위 3개 키워드 추출
		String[] genres = getTopGenresFromKeywords(playlistKeywords);

		model.addAttribute("genres", genres);
		return "playlist";
	}

	private String[] getPlaylistKeywords() {
		// TODO Auto-generated method stub
		return null;
	}

	private String[] getTopGenresFromKeywords(String[] keywords) {
	    // OpenAI API를 사용하여 키워드를 입력하고 상위 3개의 키워드를 추출
	    StringBuffer prompt = new StringBuffer();
	    prompt.append("Json 형식으로 ")
	            .append(Arrays.toString(keywords))
	            .append(" 노래 전체가 ")
	            .append(Arrays.toString(GENRES))
	            .append(" 중에서 어떤 장르에 가까운지 상위 3개의 비율을 보여줘. ")
	            .append("비율은 전체 100% 기준이고, 3개의 키워드가 합쳐서 100%가 되야해.");
		
	    return keywords;
		
	}	

}
