package com.multi.fourtunes.model.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/AIkeyword")
public class AIKeywordController {

	/* 
	   이미 DB에 저장되어 있는 노래들("SONG_NO": 508 까지)에 OpenAIkeyword를 넣어주는 Controller
	   임의로 AIkeyword.JSON 파일 수정해서 넣어도 문제 없음
	   mypage_user.html에 주석 처리된 실행 버튼(AI키워드 insert 버튼) 있음
	*/
	
	// AIkeyword.JSON 파일 저장 위치
    private static final String JSON_FILE_PATH = "src/main/resources/static/js/AIkeyword.json";

    @Autowired
    private SongRepository songRepository;

    // Jackson 라이브러리 사용해서 JSON > JAVA parsing
    // JSON 데이터를 읽어서 SongEntity의 AIKEYWORD에 저장
    @GetMapping("/insertJSON")
    public void insertJSON() {
        try {
        	// JSON 파일 읽기
            ObjectMapper objectMapper = new ObjectMapper();
            Path jsonFilePath = Paths.get(JSON_FILE_PATH);
            // JSON 파일을 byte 배열로 읽어옴
            byte[] jsonData = Files.readAllBytes(jsonFilePath);
            
            // ObjectMapper 사용하여 JSON 파일의 데이터 JAVA 객체로 변환 후 jsonList에 저장
            // TypeReference : 변환 결과의 타입을 정의, 이 경우 List<Map<String, Object>> 타입으로 변환
            List<Map<String, Object>> jsonList = objectMapper.readValue(jsonData, new TypeReference<>() {});

            // jsonList 반복하면서 SongEntity에 AIKEYWORD 저장
            for (Map<String, Object> data : jsonList) {
            	// SONG_NO long 타입으로 추출
                Long songNo = ((Integer) data.get("SONG_NO")).longValue();
                // AIKEYWORD 추출
                String aiKeyword = (String) data.get("AIKEYWORD");

                // SONG_NO로 조회하고 저장
                SongEntity songEntity = songRepository.findBySongNo(songNo);
                if (songEntity != null) {
                    songEntity.setSongAikeyword(aiKeyword);
                    songRepository.save(songEntity);
                }
                
            }
            // 하얀 화면, 콘솔창에 메시지 뜨면 성공
            // log.info("JSON 데이터가 성공적으로 insert 되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}