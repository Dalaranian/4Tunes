package com.multi.fourtunes.model.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.entity.UserEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import com.multi.fourtunes.model.jpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.multi.fourtunes.model.biz.PlaylistBiz;
import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/playlist")
public class PlayListController {

    @Autowired
    PlaylistBiz playlist;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SongRepository songRepository;

    @PostMapping("/insertmyplaylist")
    public ResponseEntity<String> addToPlaylist(@RequestBody SongDto songDto, HttpSession session) {
//        log.info(songDto);
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

            try {
                // 본인 플레이리스트 구분 가능하게 식별자 추가
                myPlaylists.get(0).setUserName(myPlaylists.get(0).getUserName() + "(나) ");
            } catch (IndexOutOfBoundsException e) {
//                e.printStackTrace();
                log.info("관리자 접속 예외 처리");
            }
        } else {
            allPlaylists = playlist.getAllPlaylists();
        }

        // myPlaylists와 allPlaylists 병합
        List<PlaylistDto> playlists = new ArrayList<>();
        playlists.addAll(myPlaylists);
        playlists.addAll(allPlaylists);

        log.info("Load playlist = {}", playlists);

        return ResponseEntity.ok(playlists);
    }

    /**
     * @param userNo  조회할 PlayList의 UserNo
     * @param session 세션에 담긴 로그인한 유저의 정보 불러오기
     * @return Public / Mine 의 플레이리스트 페이지를, SongDto 가 담긴 ArrayList에 담아서 전송
     */
    @GetMapping("/gotoplaylistdetail")
    public ModelAndView gotoPlayListDetail(String userNo, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        // 세션의 UserDto 불러오기
        UserDto currentUser = (UserDto) session.getAttribute("login");

        // 현재 유저 소유의 플레이리스트인지, 아니면 타인의 플레이리스트인지 확인하여 return 할 view 결정
        if (Integer.parseInt(userNo) == currentUser.getUser_no()) {
            modelAndView.setViewName("playlist_mine");
//            log.info("내꺼");
        } else {
            UserEntity owner = userRepository.findByUserNo(Integer.parseInt(userNo));
            modelAndView.setViewName("playlist_public");
            modelAndView.addObject("name", owner.getUserName());
//            log.info("남꺼");
        }

        // PlayList에 담겨있는 모든 노래를 페이징을 위해 세션에 저장함
        List<SongDto> songs = playlist.getPlayListSongs(userNo);
        HashMap<String, List<SongDto>> currentPlayList = new HashMap<>();
        currentPlayList.put(userNo, songs);
        session.removeAttribute("currentPlayList");
        session.setAttribute("currentPlayList", currentPlayList);
//        modelAndView.addObject("songs", songs);

        // 화면에 보여줄 SongDto 8개짜리 List를 만듬
        List<SongDto> finalResult = new ArrayList<>();
        try {
            for (int i = 0; i < 8; i++) {
                finalResult.add(songs.get(i));
            }
        } catch (IndexOutOfBoundsException e) {
            log.info("log : 불러온 플레이리스트 갯수가 8개 이하");
            e.printStackTrace();
        }
        modelAndView.addObject("songs", finalResult);
        return modelAndView;
    }

    /**
     * @param song    삭제할 노래의 JPA Entity
     * @param session
     * @return 삭제 성공 여부를 String 으로 보냄
     */
    @PostMapping("/deletemyplaylist")
    public String deleteMyPlaylist(@RequestBody SongEntity song, HttpSession session) {

        UserDto currentUser = (UserDto) session.getAttribute("login");

        String res = playlist.deleteMyPlaylist(song, currentUser);

        return (res.equals("success")) ? "내 플레이리스트에서 삭제되었습니다." : "삭제 실패하였습니다. 다시 시도해주세요.";
    }

    /**
     * 플레이스트
     *
     * @param request 공개를 요청했을때 True, 비공개를 요청했을때 False
     * @return
     */
    @PostMapping("/managevisibility")
    public ResponseEntity<String> manageVisibility(@RequestBody boolean request, HttpSession session) {
        UserDto currentLogin = (UserDto) session.getAttribute("login");

        String res = playlist.visibilityManage(currentLogin, request);

        // 요청에 대한 응답 반환
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("getmoresong")
    public ResponseEntity<List<SongDto>> getMoreSongs(@RequestBody String requestJsonString, HttpSession session) throws JsonProcessingException {

        List<SongDto> dtos = playlist.getSongDtos(requestJsonString, session);

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
