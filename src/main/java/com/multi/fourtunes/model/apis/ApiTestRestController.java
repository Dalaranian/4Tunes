package com.multi.fourtunes.model.apis;

import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiTestRestController {

    @Autowired
    YoutubeApi youtube;

    @Autowired
    ManiaDbApi mania;

    @Autowired
    ApiTestService service;

    @Autowired
    OpenAiApi openAiApi;

    @GetMapping("/jpatest")
    public ModelAndView testJpa(String query) {

        mania.setPrompt(query);
        mania.setType(false);
        List<SongDto> result = mania.search();

        for(SongDto res:result){

            SongEntity song = new SongEntity();

            song.setSongArtist(res.getSongArtist());
            song.setSongTitle(res.getSongTitle());
            song.setSongLink("temp");
            song.setSongId(res.getSongId());
            // log.info(song.toString());

            service.saveSongEntitiy(song);
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/gpt")
    public ResponseEntity<String> openAi(String[] keyword){

        // log.info(Arrays.toString(keyword));

        //openAiApi.suggestedSong(new String[] {"kpop"});

        return null;
    }
}
