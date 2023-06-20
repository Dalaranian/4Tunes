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

	@GetMapping("/chart/top10")
    public String getTop10Chart(Model model) {
        List<SongDto> top10Songs = chartBiz.getTop10Chart();

        model.addAttribute("songs", top10Songs);
        return "chartpage_main";
    }

}
