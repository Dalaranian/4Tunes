package com.multi.fourtunes.model.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 조장
 *	API 테스트를 위한 임시 컨트롤러입니다. 
 */
@Controller
public class ApiTestController {
	
	// 이런식으로 꼭 해줘야 한다고 함
	@Autowired
	YoutubeApi getLink;
	
	@GetMapping("/gototestpage")
	public String pageMove() {
		return "apitestpage";
	}

	@GetMapping("/ManiaDB")
	public void test1() {
		
	}
	
	@GetMapping("/YoutubeData")
	public String test2(String singer, String title) {
		
		System.out.println("YouTubeController 진입");
		
//		YoutubeApi getLink = new YoutubeApi();
		
		String link = getLink.embedLinkGetter(singer, title);
		
		System.out.println(link);
		
		return "apitestpage";
	}
	
}
