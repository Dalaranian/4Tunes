package com.multi.fourtunes.model.dto;

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
	private int songNo;
	private String songTitle;
	private String songArtist;
	private String songLink;
}
