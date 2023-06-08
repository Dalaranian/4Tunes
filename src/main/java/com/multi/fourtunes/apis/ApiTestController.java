package com.multi.fourtunes.apis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 조장
 *	API 테스트를 위한 임시 컨트롤러입니다. 
 */
@Controller
public class ApiTestController {
	
	@GetMapping("/gototestpage")
	public String pageMove() {
		return "apitestpage";
	}

	@GetMapping("/ManiaDB")
	public void test1() {
		
	}
	
	@GetMapping("/YoutubeData")
	public void test2() {
		YoutubeApi getLink = new YoutubeApi();
		getLink.embedLinkGetter("아이유","밤편지");
	}
	
}
