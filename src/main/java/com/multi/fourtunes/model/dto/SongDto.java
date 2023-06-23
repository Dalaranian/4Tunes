package com.multi.fourtunes.model.dto;

import lombok.Data;

@Data
public class SongDto {

    private Integer songNo;
    private String songTitle;
    private String songArtist;
    private String songAlbumArt;
    private String songLink;
    private String songId;
    private int playlistCount;
}