package com.multi.fourtunes.model.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.fourtunes.model.biz.PlaylistBiz;
import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;

@RestController
@RequestMapping("/playlist")
public class PlayListController {

    @Autowired
    PlaylistBiz playlist;

    ResponseEntity<String> response;

//    @PostMapping("/insertmyplaylist")
//    public ResponseEntity<String> addToPlaylist(@RequestBody SongDto songDto, HttpSession session) {
//        UserDto currentUser = (UserDto) session.getAttribute("login");
//        String res = playlist.insertPlaylist(songDto, currentUser);
//
//        if(res.equals(" 플레이리스트 저장에 성공했습니다. ")){
//            response = ResponseEntity.status(HttpStatus.OK).body("res");
//        }else{
//            response = ResponseEntity.status(HttpStatus.ACCEPTED).body("res");
//        }
//
//        return response;
//    }

    @PostMapping("/insertmyplaylist")
    public ResponseEntity<String> addToPlaylist(@RequestBody SongDto songDto, HttpSession session) {
        System.out.println(songDto);
        UserDto currentUser = (UserDto) session.getAttribute("login");
        String res = playlist.insertPlaylist(songDto, currentUser);

        if (res.equals("플레이리스트 저장에 성공했습니다.")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.accepted().body(res);
        }
    }
    
    @GetMapping("/getmyplaylist")
    public ResponseEntity<List<PlaylistDto>> home() {
        List<PlaylistDto> playlists = playlist.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }
}
