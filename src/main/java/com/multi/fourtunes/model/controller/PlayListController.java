package com.multi.fourtunes.model.controller;

import com.multi.fourtunes.model.biz.PlaylistBiz;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/playlist")
public class PlayListController {

    @Autowired
    PlaylistBiz playlist;

    @PostMapping("/insertmusic")
    public ResponseEntity<String> addToPlaylist(@RequestBody SongDto songDto, HttpSession session) {
        UserDto currentUser = (UserDto) session.getAttribute("login");
        String res = playlist.insertPlaylist(songDto, currentUser);

        return ResponseEntity.status(HttpStatus.OK).body("곡 추가 완료");
    }
}
