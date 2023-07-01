package com.multi.fourtunes.model.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "MANAGE_SONG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManageSongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_no")
    private PlaylistEntity playlist;

    @ManyToOne
    @JoinColumn(name = "song_no")
    private SongEntity song;
}