package com.multi.fourtunes.model.jpa.entitiy;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "SONG")
@Data
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SONG_NO")
    private Integer songNo;

    @Column(name = "SONG_TITLE", nullable = false)
    private String songTitle;

    @Column(name = "SONG_ARTIST", nullable = false)
    private String songArtist;

    @Column(name = "SONG_LINK", nullable = false)
    private String songLink;

    @Column(name = "SONG_ID", unique = true)
    private String songId;
}
