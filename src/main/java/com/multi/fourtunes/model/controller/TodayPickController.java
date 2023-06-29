package com.multi.fourtunes.model.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.multi.fourtunes.model.biz.SuggestBiz;
import com.multi.fourtunes.model.dto.SongDto;

@Controller
@RequestMapping("/4tunes")
public class TodayPickController {
	
	@Autowired
	SuggestBiz suggestBiz;

	@RequestMapping("/insert")
	public String insertTodayPick(Model model) {
		ArrayList<SongDto> songs = new ArrayList<>();
		
		SongDto pick = new SongDto();
		pick.setSongTitle("너에게 난, 나에게 넌");
		pick.setSongArtist("미도와 파라솔");
		songs.add(pick);
		
		SongDto pick2 = new SongDto();
		pick2.setSongTitle("그대라는 시");
		pick2.setSongArtist("태연");
		songs.add(pick2);

		SongDto pick3 = new SongDto();
		pick3.setSongTitle("좋니");
		pick3.setSongArtist("윤종신");
		songs.add(pick3);

		SongDto pick4 = new SongDto();
		pick4.setSongTitle("첫눈처럼 너에게 가겠다");
		pick4.setSongArtist("에일리");
		songs.add(pick4);

		SongDto pick5 = new SongDto();
		pick5.setSongTitle("잊어야 한다는 마음으로");
		pick5.setSongArtist("김광석");
		songs.add(pick5);

		SongDto pick6 = new SongDto();
		pick6.setSongTitle("그때 헤어지면 돼");
		pick6.setSongArtist("로이킴");
		songs.add(pick6);

		SongDto pick7 = new SongDto();
		pick7.setSongTitle("밤편지");
		pick7.setSongArtist("아이유");
		songs.add(pick7);

		SongDto pick8 = new SongDto();
		pick8.setSongTitle("그대라는 사치");
		pick8.setSongArtist("임창정");
		songs.add(pick8);

		SongDto pick9 = new SongDto();
		pick9.setSongTitle("아로하");
		pick9.setSongArtist("조정석");
		songs.add(pick9);

		SongDto pick10 = new SongDto();
		pick10.setSongTitle("거짓말이라도 해서 널 보고싶어");
		pick10.setSongArtist("백지영");
		songs.add(pick10);
		
		//System.out.println("음악: " + songs);
		
		ArrayList<SongDto> res = suggestBiz.searchSuggestedSong(songs);
		model.addAttribute("todayPickResult", res);
		
		
		return "index";
	}
	
	@RequestMapping("/suggest/{todayPickNo}")
	public String gotoTodayPick(@PathVariable int todayPickNo) {
		
		
		return "playlist_todaypick";
	}
	
	
}
