package com.multi.fourtunes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@EnableSwagger2
@EnableScheduling
@SpringBootApplication
@Controller
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	// 루트 요청(페이지를 index.html 로 보내기)
	@RequestMapping("/")
	public String root(HttpServletRequest request) {
		// Root 에 접속한 유저 정보 Info에 띄우기
		log.info("접속 유저 = {}", request.getHeader("User-Agent"));
		log.info("접속 아이피 = {}", request.getRemoteAddr());
		return "index";
	}

}
