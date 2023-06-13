package com.multi.fourtunes.model.apis;

import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiTestService {
    private final SongRepository songRepository;

    @Autowired
    public ApiTestService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void saveSongEntitiy(SongEntity entity) {
        System.out.println("service : " + entity.toString());

        try {
            songRepository.save(entity);
        } catch (Exception e) {
            System.out.println("Duplicate Insert : " + entity);
        }
    }
}
