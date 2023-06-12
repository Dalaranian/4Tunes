package com.multi.fourtunes.model.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.apis.ManiaDbApi;
import com.multi.fourtunes.apis.YoutubeApi;
import com.multi.fourtunes.model.dto.SongDto;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	ManiaDbApi mania;

	@Autowired
	YoutubeApi youtube;

	@GetMapping("/query")
	public String search(String query, Model model) {
//		System.out.println(query);

		// 검색 로직
		mania.setPrompt(query);
		mania.setType(false);
		ArrayList<SongDto> result = mania.search();
		result.stream().forEach(currentSong -> currentSong
				.setSongLink(youtube.embedLinkGetter(currentSong.getSongArtist(), currentSong.getSongTitle())));
		for(SongDto dto:result) {
			System.out.println(dto);
		}
		model.addAttribute("qeury", query);
		model.addAttribute("searchresult", result);

		return "playlist_searchresult"; // 결과를 표시할 View의 이름을 반환
	}
}
