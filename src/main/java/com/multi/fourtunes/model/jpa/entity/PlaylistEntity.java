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
	private int playlistNo;

	@OneToMany(mappedBy = "playlist")
	private List<ManageSongEntity> manageSongs;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_no")
	private UserEntity user;
}