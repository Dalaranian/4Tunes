package com.multi.fourtunes.model.dto;

import lombok.Data;

@Data
public class PlaylistDto {
	private int playlistNo;
	private String playlistName;
	private int userNo;
	private String playlistVisibility; // 플레이리스트 공개여부
	private String albumArt;
	private String userName;
}
