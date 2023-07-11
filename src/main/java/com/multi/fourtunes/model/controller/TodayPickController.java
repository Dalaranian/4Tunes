package com.multi.fourtunes.model.controller;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.TodayPickBiz;
import com.multi.fourtunes.model.dto.SongDto;

@Slf4j
@Controller
@RequestMapping("/4tunes")
public class TodayPickController {
	
	@Autowired
	TodayPickBiz todayPickBiz;
	
	@GetMapping("/suggest/{todayPickNo}")
	public String todayPickDetail(@PathVariable int todayPickNo, Model model) {
		//// log.info("번호: " + todayPickNo);
		
		// todayPickNo에 해당하는 플레이리스트의 이름 받아오기
		String playlistName = todayPickBiz.getTodayPickName(todayPickNo);
		model.addAttribute("playlistName", "#"+playlistName);
		
		// todayPickNo에 해당하는 플레이리스트 내용 받아오기
		ArrayList<SongDto> songs = todayPickBiz.selectTodayPickList(todayPickNo);
		model.addAttribute("songs", songs);
		
		return "playlist_todaypick";
	}

}
