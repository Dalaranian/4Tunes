package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.mapper.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongDaoImple implements SongDao{

    @Autowired
    SongMapper songMapper;

    @Override
    public SongDto SelectSongById(String id) {
        return null;
    }

    /**
     * @param playListNo
     * @param userNo
     * @return 플레이리스트 No와 UserNo 에 해당하는 플레이리스트에 담긴 SongDto 를 ArrayList 로 리턴
     */
    @Override
    public List<SongDto> selectSongListByPlayListNo(int playListNo, int userNo) {
        return songMapper.selectSongListByPlayListNo(playListNo, userNo);
    }
}
