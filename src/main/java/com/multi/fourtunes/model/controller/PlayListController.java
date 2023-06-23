package com.multi.fourtunes.model.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


    @PostMapping("/insertmyplaylist")
    public ResponseEntity<String> addToPlaylist(@RequestBody SongDto songDto, HttpSession session) {
        System.out.println(songDto);
        UserDto currentUser = (UserDto) session.getAttribute("login");
        String res = playlist.insertPlaylist(songDto, currentUser);

        System.out.println(" 여기서 에러 나오겠지? \n" + res);

        if (res.equals("플레이리스트 저장에 성공했습니다.")) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.accepted().body(res);
        }
    }
    
    @GetMapping("/getmyplaylist")
    public ResponseEntity<List<PlaylistDto>> getMyPlaylist(HttpSession session) {
        UserDto currentUser = (UserDto) session.getAttribute("login");
        List<PlaylistDto> myPlaylists = new ArrayList<>();
        List<PlaylistDto> allPlaylists = new ArrayList<>();

        if (currentUser != null) {
            myPlaylists = playlist.getMyPlaylist(currentUser.getUser_no());
            allPlaylists = playlist.getAllPlaylists();

            // myPlaylists에 이미 있는 플레이리스트 제외
            allPlaylists.removeAll(myPlaylists);
        }else {
            allPlaylists = playlist.getAllPlaylists();
        }

        // myPlaylists와 allPlaylists 병합
        List<PlaylistDto> playlists = new ArrayList<>();
        playlists.addAll(myPlaylists);
        playlists.addAll(allPlaylists);
        
        
        return ResponseEntity.ok(playlists);
    }
}
