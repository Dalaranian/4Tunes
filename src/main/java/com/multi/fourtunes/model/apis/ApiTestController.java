package com.multi.fourtunes.model.apis;

import com.multi.fourtunes.model.dto.SongDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

/**
 * @author 조장
 *	API 테스트를 위한 임시 컨트롤러입니다. 
 */
@Slf4j
@Controller
public class ApiTestController {
	@Autowired
	YoutubeApi youtube;

	@Autowired
	ManiaDbApi mania;

	@GetMapping("/gototestpage")
	public String pageMove() {
		return "apitestpage";
	}

	@GetMapping("/ManiaDB")
	public String test1(String query) {
//		// log.debug(query);

		mania.setPrompt(query);
		mania.setType(false);
		ArrayList<SongDto> dtos = mania.search();
		for (SongDto current:dtos){
			// log.debug(current.toString());
		}

		ArrayList<SongDto> result = mania.search();
		result.stream().forEach(currentSong -> currentSong
				.setSongLink(youtube.embedLinkGetter(currentSong.getSongArtist(), currentSong.getSongTitle())));

		for(SongDto dto:result){
			// log.debug(dto.toString());
		}

		return "apitestpage";
	}
	
	@GetMapping("/YoutubeData")
	public String test2(String singer, String title) {
		
		// log.debug("YouTubeController 진입");
		
//		YoutubeApi getLink = new YoutubeApi();
		
		String link = youtube.embedLinkGetter(singer, title);
		
		// log.debug(link);
		
		return "apitestpage";
	}
	
}
