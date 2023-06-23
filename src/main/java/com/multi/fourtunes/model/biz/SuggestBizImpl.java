package com.multi.fourtunes.model.biz;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.apis.ManiaDbApi;
import com.multi.fourtunes.model.apis.YoutubeApi;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;

@Service
public class SuggestBizImpl implements SuggestBiz {

	@Autowired
	ManiaDbApi mania;

	@Autowired
	SongRepository songRepository;
	
	@Autowired
	YoutubeApi youtubeApi;

	/**
	 * Chat GPT 에서 받은 결과(Artist, title) 를 토대로, 해당 정보를 MeniaDb 에 검색한다. <br>
	 * 불러온 10개의 ManiaDB 정보 중, Artist 와 title 이 contain된 정보를 filtering 한다. <br>
	 * filtering 한 정보들을 filterRes 에 저장한 후, finalRes 에 filterRes.get(0) 한 값을 저장한다.
	 * <br>
	 * 반목문을 통해, ChatGPT 로 받은 모든 정보에 대해 위와 같은 과정을 수행한 후, 최종 결과 finalRes 를 리턴한다.
	 * 
	 */
	@Override
	public ArrayList<SongDto> searchSuggestedSong(ArrayList<SongDto> song) {

		ArrayList<SongDto> filterRes, finalRes;

		finalRes = new ArrayList<>();

		for (SongDto suggest : song) {
			filterRes = new ArrayList<>();
			String title = suggest.getSongTitle();
			mania.setPrompt(title);
			mania.setType(false);
			ArrayList<SongDto> result = mania.search();
			System.out.println("검색 결과: " + result);

			for (SongDto res : result) {
				SongEntity songEntitiy = songRepository.findBySongId(res.getSongId());
				if(songEntitiy != null) {
					res.setSongLink(songEntitiy.getSongLink());
					filterRes.add(res);
				}
				else if (res.getSongArtist().replace(" ", "").contains(suggest.getSongArtist())
							&& res.getSongTitle().replace(" ", "").contains(suggest.getSongTitle())) {
					res.setSongLink(youtubeApi.embedLinkGetter(res.getSongArtist(), res.getSongTitle()));
					filterRes.add(res);
				}
			}

			try {
				finalRes.add(filterRes.get(0));
			} catch (java.lang.IndexOutOfBoundsException e) {
				System.out.println(suggest.getSongTitle() + "에 관한 결과 없음");
				continue;
			}
			System.out.println("**최종 결과: " + finalRes);
		}
		return finalRes;
	}

}
