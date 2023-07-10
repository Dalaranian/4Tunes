package com.multi.fourtunes.model.biz;

import java.util.ArrayList;

import com.multi.fourtunes.model.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.fourtunes.model.apis.ManiaDbApi;
import com.multi.fourtunes.model.apis.YoutubeApi;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Service
public class SuggestBizImpl implements SuggestBiz {

	@Autowired
	ManiaDbApi mania;

	@Autowired
	SongRepository songRepository;
	
	@Autowired
	YoutubeApi youtubeApi;

	@Autowired
	UserDao userDao;

	/**
	 * Chat GPT 에서 받은 결과(artist, title) 를 토대로, 해당 정보를 ManiaDb 에 검색한다. <br>
	 * 불러온 10개의 ManiaDB 정보 중, artist 와 title 이 contain된 정보를 filtering 한다. <br>
	 * filtering 한 정보들을 filterRes 에 저장한 후, finalRes 에 filterRes.get(0) 한 값을 저장한다. <br>
	 * 반복문을 통해, ChatGPT 로 받은 모든 정보에 대해 위와 같은 과정을 수행한 후, 최종 결과 finalRes 를 리턴한다.
	 * 
	 */
	@Override
	public ArrayList<SongDto> searchSuggestedSong(ArrayList<SongDto> songs) {

		// 필터링(artist와 title이)된 노래를 담을 filterRes와 최종 검색결과를 담을 finalRes 선언
		ArrayList<SongDto> filterRes, finalRes;
		finalRes = new ArrayList<>();

		// OpenAI가 추천한 노래 10곡을 하나씩 꺼내어 ManiaDB에 검색
		for (SongDto suggest : songs) {
			filterRes = new ArrayList<>();
			
			String title = suggest.getSongTitle();
			mania.setPrompt(title);
			mania.setType(false);
			
			// ManiaDB를 통해 검색 결과 10개가 담겨있는 searchResult를 받아옴
			ArrayList<SongDto> searchResult = mania.search();
//			log.info("검색 결과: " + searchResult);

			
			for (SongDto res : searchResult) {
				// 검색 결과 10개를 하나씩 꺼내어, DB에 이미 저장되어있는 노래인지 확인 과정 거침 (JPA 활용)
				SongEntity songEntitiy = songRepository.findBySongId(res.getSongId());
				if(songEntitiy != null && res.getSongArtist().replace(" ", "").toUpperCase().contains(suggest.getSongArtist().toUpperCase())) {  // DB에 이미 있는 노래이면, DB에 저장되어있는 youtubeLink 저장
					res.setSongLink(songEntitiy.getSongLink());
					filterRes.add(res);
				}
				else if (res.getSongArtist().replace(" ", "").contains(suggest.getSongArtist())
							&& res.getSongTitle().replace(" ", "").toUpperCase().contains(suggest.getSongTitle().toUpperCase())) {
					try {
						res.setSongLink(youtubeApi.embedLinkGetter(res.getSongArtist(), res.getSongTitle()));
						filterRes.add(res);
					} catch (HttpClientErrorException e) {
						e.printStackTrace();
//						throw new RuntimeException(e);
					}
					// embedLinkGetter(res.getSongArtist(), res.getSongTitle())
					// testLinkGetter()
//					filterRes.add(res);
				}
			}

			try {
				// 한 노래에 대해 여러 검색결과가 있는 경우, 첫번째 검색결과만 finalRes에 저장
				//log.info("걸러진 친구들 은 \n"+filterRes);
				finalRes.add(filterRes.get(0));
			} catch (java.lang.IndexOutOfBoundsException e) {
//				log.info(suggest.getSongTitle() + "에 관한 결과 없음");
				continue;
			}
//			log.info("**최종 결과: " + finalRes);
		}
		return finalRes;
	}

	/**
	 * @param userNo suggestCount 에 +1 할 유저의 번호
	 * @param count update 할 suggestCount
	 */
	@Override
	public void addSuggestCount(Integer userNo, int count) {
		userDao.addSuggestCount(userNo, count);
	}

	@Override
	public void seggestCountReset() {
		userDao.suggestCountReset();
	}

}
