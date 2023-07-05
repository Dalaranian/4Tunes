package com.multi.fourtunes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDto {

    private Integer songNo;
    private String songTitle;
    private String songArtist;
    private String songAlbumArt;
    private String songLink;
    private String songId;
    private String songAikeyword;
    private int playlistCount;
}