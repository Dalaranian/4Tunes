package com.multi.fourtunes.model.apis;

import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiTestService {
    private final SongRepository songRepository;

    @Autowired
    public ApiTestService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void saveSongEntitiy(SongEntity entity) {
        // log.info("service : " + entity.toString());

        try {
            songRepository.save(entity);
        } catch (Exception e) {
            // log.info("Duplicate Insert : " + entity);
        }
    }
}
