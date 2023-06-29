package com.multi.fourtunes.model.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.multi.fourtunes.model.jpa.entity.SongEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multi.fourtunes.model.biz.PlaylistBiz;
import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     *
     * @param userNo 조회할 PlayList의 UserNo
     * @param session 세션에 담긴 로그인한 유저의 정보 불러오기
     * @return Public / Mine 의 플레이리스트 페이지를, SongDto 가 담긴 ArrayList에 담아서 전송
     */
    @GetMapping("/gotoplaylistdetail")
    public ModelAndView gotoPlayListDetail(String userNo, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();

        // 세션의 UserDto 불러오기
        UserDto currentUser = (UserDto) session.getAttribute("login");

        // 현재 유저 소유의 플레이리스트인지, 아니면 타인의 플레이리스트인지 확인하여 return 할 view 결정
        if(Integer.parseInt(userNo) == currentUser.getUser_no()){
            modelAndView.setViewName("playlist_mine");
//            System.out.println("내꺼");
        }else{
            modelAndView.setViewName("playlist_public");
//            System.out.println("남꺼");
        }

        // PlayList에 담겨있는 Song을 addobject 함
        List<SongDto> songs = playlist.getPlayListSongs(userNo);

        modelAndView.addObject("songs", songs);

        return modelAndView;
    }

    /**
     *
     * @param song 삭제할 노래의 JPA Entity
     * @param session
     * @return 삭제 성공 여부를 String 으로 보냄
     */
    @PostMapping("/deletemyplaylist")
    public String deleteMyPlaylist(@RequestBody SongEntity song, HttpSession session){

        UserDto currentUser = (UserDto) session.getAttribute("login");

        String res = playlist.deleteMyPlaylist(song, currentUser);

        return (res.equals("success"))?"삭제 성공":"삭제 실패";
    }
}
