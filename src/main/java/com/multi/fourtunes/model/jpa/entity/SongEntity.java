package com.multi.fourtunes.model.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "SONG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SONG_NO")
    private Long songNo;

    @Column(name = "SONG_TITLE", nullable = false)
    private String songTitle;

    @Column(name = "SONG_ARTIST", nullable = false)
    private String songArtist;

    @Column(name = "SONG_LINK", nullable = false)
    private String songLink;

    @Column(name = "SONG_ID", unique = true)
    private String songId;

    @Column(name = "SONG_ALBUMART", nullable = true)
    private String songAlbumart;
    
    @Column(name = "SONG_AIKEYWORD", nullable = true)
    private String songAikeyword;
}
