package com.multi.fourtunes.model.dto;

import lombok.Data;

// lombok 을 이용한 DTO 생성
@Data
public class SongDto {
	String seq;
	String title;
	String artist;
	String link;
}
