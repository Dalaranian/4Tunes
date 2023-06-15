package com.multi.fourtunes.model.controller;

import java.util.ArrayList;

import com.multi.fourtunes.model.apis.ManiaDbApi;
import com.multi.fourtunes.model.apis.YoutubeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.dto.SongDto;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	YoutubeApi youtube;

	@Autowired
	ManiaDbApi mania;

	@GetMapping("/query")
	public ModelAndView search(String query) {
//		System.out.println(query);

		ModelAndView modelAndView = new ModelAndView();

		// query 로 검색 시작
		mania.setPrompt(query);
		mania.setType(false);
		ArrayList<SongDto> result = mania.search();
//		result.stream().forEach(currentSong -> currentSong
//				.setSongLink(youtube.embedLinkGetter(currentSong.getSongArtist(), currentSong.getSongTitle())));

		// test 를 위해 더미데이터 넣기
		for(SongDto dto:result) {
			dto.setSongLink(youtube.testLinkGetter());
		}

		modelAndView.addObject("query", query);
		modelAndView.addObject("searchresult", result);
		modelAndView.setViewName("playlist_searchresult");
//		model.addAttribute("query", query);
//		model.addAttribute("searchresult", result);

		return modelAndView;
	}
}
