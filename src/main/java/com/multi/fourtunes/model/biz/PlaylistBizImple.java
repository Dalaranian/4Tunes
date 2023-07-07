package com.multi.fourtunes.model.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Random;
import java.util.Set;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.fourtunes.model.dao.SongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.multi.fourtunes.model.apis.AnalysisApi;
import com.multi.fourtunes.model.dao.PlaylistDao;
import com.multi.fourtunes.model.dto.PlaylistDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.entity.UserEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import com.multi.fourtunes.model.jpa.repository.UserRepository;
import com.multi.fourtunes.model.mapper.KeywordMapper;
import com.multi.fourtunes.model.mapper.PlayListMapper;
import com.multi.fourtunes.model.mapper.UserMapper;

import javax.servlet.http.HttpSession;

@Service
public class PlaylistBizImple implements PlaylistBiz {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SongRepository songRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PlayListMapper playListMapper;
    @Autowired
	KeywordMapper keywordMapper;	
    @Autowired
    PlaylistDao playlistDao;
    @Autowired
    SongDao songDao;
	@Autowired
	AnalysisApi analysisApi;


    /**
     * SongDto 를 받아서, 유저의 첫번째 PlayList 에 저장함.
     *
     * @param song 저장할 SongDto
     * @param user 저장할 User
     * @return 성공여부에 대해서, alert 로 띄워줄 Message
     */
	@Override
	public String insertPlaylist(SongDto song, UserDto user) {
		String result;

		// System.out.println(song + "\n" + user);

		// 추가하려는 노래가 DB 에 존재하는지 확인
		// SongDto selectSong = songDao.SelectSongById(song.getSongId());
		try {
			SongEntity selectSong = songRepository.findBySongId(song.getSongId());
			// 노래가 없을시, DB 에 우선 저장
			if (selectSong == null) {

				// 빈 SongEntity 생성
				selectSong = new SongEntity();

				// entity 준비
				selectSong.setSongArtist(song.getSongArtist());
				selectSong.setSongLink(song.getSongLink());
				selectSong.setSongTitle(song.getSongTitle());
				selectSong.setSongId(song.getSongId());
				selectSong.setSongAlbumart(song.getSongAlbumArt());
				
//				// AIKeyword 넣는 부분
//				// 저장하려는 노래의 정보 가져오기
//				String songInfo = song.getSongTitle() + "-" + song.getSongArtist();
//
//				// AI 키워드 추출
//				List<String> AIKeyword = analysisApi.suggestedAIKeyword(new String[] { songInfo });
//				if (!AIKeyword.isEmpty()) {
//					// 첫 번째 추출된 키워드를 선택하여 저장
//					selectSong.setSongAikeyword(AIKeyword.get(0));
//				}

				// entity 저장
				songRepository.save(selectSong);
				// 저장되어서, Auto Increment 값이 반영된 새로운 엔티티 불러오기
				selectSong = songRepository.findBySongId(song.getSongId());

			}

			// User 의 PlayListNo 를 조회
			String[] userPlayList = userMapper.getUserPlatListNo(Integer.toString(user.getUser_no()));

			// 첫 번째 PlayList 에 노래 넣기
			int res = playlistDao.insertPlaylist(userPlayList[0], Long.toString(selectSong.getSongNo()));

			result = " 플레이리스트 저장에 성공했습니다. ";
		} catch (DuplicateKeyException e) {
			result = song.getSongTitle() + " 는 이미 저장된 노래입니다. ";
		} catch (Exception e) {
			result = " 플레이리스트 저장에 실패했습니다. 다시 시도해주세요 ";
		}

		return result;
	}

    @Override
    public void allocatePlaylist(String userId) {
        UserEntity user = userRepository.findByUserId(userId);
        playlistDao.allocatePlaylist(user.getUserNo());
    }

    @Override
    public List<PlaylistDto> getAllPlaylists() {
        List<PlaylistDto> playlists = playlistDao.selectAll();

        // 각 PlaylistDto의 albumImage 설정
        for (PlaylistDto playlist : playlists) {
            SongDto song = playListMapper.selectMostRecentSongAlbumArtInPlaylist(playlist.getPlaylistNo());
            if (song == null) {
                playlist.setAlbumArt("https://images.unsplash.com/photo-1682687980918-3c2149a8f110?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80");
            } else {
                playlist.setAlbumArt(song.getSongAlbumArt());
            }
        }

        return playlists;
    }

    @Override
    public List<PlaylistDto> getMyPlaylist(int myno) {
        List<PlaylistDto> myPlaylists = playListMapper.selectMine(myno);

        // 각 PlaylistDto의 albumImage 설정
        for (PlaylistDto playlist : myPlaylists) {
            SongDto song = playListMapper.selectMostRecentSongAlbumArtInPlaylist(playlist.getPlaylistNo());
            if (song == null) {
                playlist.setAlbumArt("https://images.unsplash.com/photo-1682687980918-3c2149a8f110?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1171&q=80");
            } else {
                playlist.setAlbumArt(song.getSongAlbumArt());
            }
        }

        return myPlaylists;
    }

    @Override
    public List<SongDto> getPlayListSongs(String userNo) {

        int[] playListNo = playlistDao.getPlayListNo(userNo);

        List<SongDto> songs = songDao.selectSongListByPlayListNo(playListNo[0], Integer.parseInt(userNo));


        return songs;
    }

    /**
     * 화면에서 불러온, SongNo 가 없는 entity에서, DB 에 저장된 Entity 를 불러와,<br>
     * SongNo 와 UserNo 를 활용하여, Song 삭제
     *
     * @param song
     * @param currentUser
     * @return
     */
    @Override
    public String deleteMyPlaylist(SongEntity song, UserDto currentUser) {

        // User의 플레이리스트 No 를 불러옴
        String[] userPlayList = userMapper.getUserPlatListNo(Integer.toString(currentUser.getUser_no()));

        // 원래 노래를 불러옴
        SongEntity originSong = songRepository.findBySongId(song.getSongId());

        int res = playlistDao.deleteMyPlayList(userPlayList[0], originSong.getSongNo());

        return (res == 1) ? "success" : "fail";
    }

    /**
     * @param currentLogin 현재 로그인한 유저
     * @param request      공개요청일때 true, 비공개요정일때 false
     * @return 처리 message
     */
    @Override
    public String visibilityManage(UserDto currentLogin, boolean request) {

        System.out.println(currentLogin + "\n" + request);

        String res = "";

        // User의 플레이리스트 No 를 불러옴
        String[] userPlayList = userMapper.getUserPlatListNo(Integer.toString(currentLogin.getUser_no()));

        // 공개중이면 Y, 비공개중일때 N
        String isVisibility = playlistDao.getPlayListVisibility(currentLogin.getUser_no());

        if (isVisibility.equals("Y") && request) {
            // 공개중, 공개요청했을때
            res = "이미 공개중인 플레이리스트입니다. ";
        } else if (isVisibility.equals("N") && !request) {
            // 비공개중, 비공개요정했을때
            res = "이미 비공개중인 플레이리스트입니다. ";
        } else if (isVisibility.equals("N") && request) {
            // 비공개중일떄, 공개하기
            playlistDao.setPlayListVisible("Y", userPlayList[0]);
            res = "공개되었습니다. ";
        } else if (isVisibility.equals("Y") && !request) {
            // 공개중일때, 비공개요청하기
            playlistDao.setPlayListVisible("N", userPlayList[0]);
            res = "비공개되었습니다. ";
        }

        return res;
    }

    @Override
    public List<SongDto> getSongDtos(String requestJsonString, HttpSession session) {
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 문자열을 JsonNode 객체로 변환
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(requestJsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 필요한 값을 파싱
        int pageNo = jsonNode.get("pageNo").asInt();
        int pageSize = jsonNode.get("pageSize").asInt();
        JsonNode userNoNode = jsonNode.get("userNo");
        List<Integer> userNoList = new ArrayList<>();
        for (JsonNode userNo : userNoNode) {
            userNoList.add(userNo.asInt());
        }

//        System.out.println("Page Number: " + pageNo);
//        System.out.println("Page Size: " + pageSize);
//        System.out.println("User Number List: " + userNoList);

        // return 할 Dtos 생성
        List<SongDto> dtos = new ArrayList<>();

        // 세션에 있는 초기에 불러온 노래 List 꺼내오기
        HashMap<String, List<SongDto>> currentPlayList = (HashMap<String, List<SongDto>>) session.getAttribute("currentPlayList");

        Set<String> keySet = currentPlayList.keySet();
        String firstKey = keySet.iterator().next();

        List<SongDto> songs = currentPlayList.get(firstKey);

        try {
            for(int i = (pageNo * pageSize);i<=(pageNo * pageSize)+pageSize;i++){
                dtos.add(songs.get(i));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index out of bounds");
//            e.printStackTrace();
        }


        return dtos;
    }


}
