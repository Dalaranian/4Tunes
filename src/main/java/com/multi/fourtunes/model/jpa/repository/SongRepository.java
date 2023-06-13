package com.multi.fourtunes.model.jpa.repository;

import com.multi.fourtunes.model.jpa.entitiy.SongEntity;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<SongEntity, Integer> {
}
