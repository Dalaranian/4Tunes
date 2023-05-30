package com.multi.fourtunes.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

// lombok 을 이용한 DTO 생성
@Data
public class SongDto {
	String seq;
    @JsonProperty("title")
	String title;
    @JsonProperty("name")
	String artist;
	String link;
}
