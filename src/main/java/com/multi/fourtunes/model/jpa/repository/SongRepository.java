package com.multi.fourtunes.model.jpa.repository;

import com.multi.fourtunes.model.jpa.entity.SongEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends CrudRepository<SongEntity, Integer> {
    SongEntity findBySongId(String songId);

	SongEntity findBySongNo(Long songNo);
}
