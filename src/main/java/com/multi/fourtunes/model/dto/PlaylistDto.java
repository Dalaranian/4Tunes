package com.multi.fourtunes.model.dto;

import lombok.Data;

@Data
public class PlaylistDto {
	private int playlistNo;
	private String playlistName;
	private int userNo;
	private String disclosure; // 근데 이거 뭐임?
}
