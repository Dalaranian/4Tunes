package com.multi.fourtunes.model.jpa.entity;

import lombok.*;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PLAYLIST")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlaylistEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PLAYLIST_NO", nullable = false, unique = true)
	private Integer playlistNo;

	@Column(name = "PLAYLIST_NAME", nullable = false)
	private String playlistName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_NO")
	private UserEntity user;

	@OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ManageSongEntity> manageSongs;

}