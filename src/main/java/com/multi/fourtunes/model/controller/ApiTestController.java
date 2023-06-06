package com.multi.fourtunes.model.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.fourtunes.model.dto.SongDto;

@RestController
public class ApiTestController {

	@GetMapping("/test")
	public ArrayList<SongDto> ApiTest(){
		
		
		
		return null;
	}
}
