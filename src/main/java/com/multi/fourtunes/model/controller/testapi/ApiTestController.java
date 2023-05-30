package com.multi.fourtunes.model.controller.testapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.fourtunes.apis.ManiaDbApi;

/**
 * @author Dalanarian
 *
 */
@RestController
@RequestMapping("/test")
public class ApiTestController {

	@GetMapping("/mania")
	public void maniaTest() {
		ManiaDbApi mda = new ManiaDbApi();
		mda.setPrompt("밤편지");
		mda.setType(false);
		mda.search();
	}
}
