package com.multi.fourtunes.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.fourtunes.model.biz.ChartBiz;
import com.multi.fourtunes.model.dto.SongDto;

@RestController
@RequestMapping("/chart")
public class ChartController {

	@Autowired
	private ChartBiz chartBiz;

	  @GetMapping("/top10songs")
	    public ResponseEntity<List<SongDto>> getTop10Songs() {
	        List<SongDto> topSongs = chartBiz.getTop10Songs();
	        return ResponseEntity.ok(topSongs);
	    }

}
