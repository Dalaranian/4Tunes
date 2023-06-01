package com.multi.fourtunes.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// lombok 을 이용한 DTO 생성
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SongDto {
	String seq;
    @JsonProperty("title")
	String title;
    @JsonProperty("name")
	String artist;
	String link;
}
