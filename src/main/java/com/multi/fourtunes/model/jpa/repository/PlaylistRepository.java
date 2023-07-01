package com.multi.fourtunes.model.jpa.repository;

import com.multi.fourtunes.model.jpa.entity.PlaylistEntity;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
    @Query("SELECT s FROM SongEntity s INNER JOIN ManageSongEntity ms ON ms.song = s WHERE ms.playlist.playlistNo = :playlistNo")
    List<SongEntity> findAllSongsByPlaylistNo(@Param("playlistNo") int playlistNo);

    @Query("SELECT p FROM PlaylistEntity p INNER JOIN p.user u WHERE u.userNo = :userNo")
    PlaylistEntity findPlaylistByUserNo(int userNo);
}