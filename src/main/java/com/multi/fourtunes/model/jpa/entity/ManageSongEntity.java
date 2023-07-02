package com.multi.fourtunes.model.jpa.entity;

import lombok.*;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "MANAGE_SONG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManageSongEntity implements Serializable {
	
	@Id
	@Column(name = "PLAYLIST_NO", nullable = false)
	private Integer playlistNo;

	@Id
	@Column(name = "SONG_NO", nullable = false)
	private Integer songNo;

	@ManyToOne
	@JoinColumn(name = "PLAYLIST_NO", insertable = false, updatable = false)
	private PlaylistEntity playlist;

	@ManyToOne
	@JoinColumn(name = "SONG_NO", insertable = false, updatable = false)
	private SongEntity song;
}
