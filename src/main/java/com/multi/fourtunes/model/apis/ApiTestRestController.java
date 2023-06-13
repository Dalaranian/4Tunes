package com.multi.fourtunes.model.apis;

import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.jpa.entitiy.SongEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiTestRestController {

    @Autowired
    YoutubeApi youtube;

    @Autowired
    ManiaDbApi mania;
    @GetMapping("/jpatest")
    public ModelAndView testJpa(String query){

        mania.setPrompt(query);
        mania.setType(false);
        List<SongDto> result = mania.search();
        
        SongEntity song = new SongEntity();

        for(SongDto res:result){
            song.setSongArtist(res.getSongArtist());
            song.setSongTitle(res.getSongTitle());
            song.setSongLink(res.getSongLink());
            song.setSongId(res.getSongId());

            System.out.println(song.toString());
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }
}
