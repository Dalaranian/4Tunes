package com.multi.fourtunes.model.biz;

import java.util.ArrayList;

import com.multi.fourtunes.model.dto.SongDto;

public interface SuggestBiz {

	ArrayList<SongDto> searchSuggestedSong(ArrayList<SongDto> songs);

	void addSuggestCount(Integer userNo, int count);

	void seggestCountReset();
}
