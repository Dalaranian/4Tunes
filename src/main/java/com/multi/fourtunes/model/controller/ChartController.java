package com.multi.fourtunes.model.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.fourtunes.model.biz.ChartBiz;
import com.multi.fourtunes.model.dto.SongDto;

@RestController
@RequestMapping("/chart")
public class ChartController {
	 @GetMapping("/topsongs")
	    public ResponseEntity<List<SongDto>> getTopSongs() {
	        List<SongDto> topSongs = ChartBiz.getTopSongs();
	        return ResponseEntity.ok(topSongs);
	    }
}
