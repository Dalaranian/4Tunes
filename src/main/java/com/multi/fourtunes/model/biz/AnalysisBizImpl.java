package com.multi.fourtunes.model.biz;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.multi.fourtunes.model.apis.AnalysisApi;
import com.multi.fourtunes.model.dto.AnalysisKeywordDto;
import com.multi.fourtunes.model.jpa.entity.ManageSongEntity;
import com.multi.fourtunes.model.jpa.entity.PlaylistEntity;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.PlaylistRepository;
import com.multi.fourtunes.model.jpa.repository.SongRepository;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalysisBizImpl implements AnalysisBiz {

	@Autowired
	PlaylistRepository playlistRepository;
	@Autowired
	SongRepository songRepository;
	@Autowired
	AnalysisApi analysisApi;

	@Override
	public List<AnalysisKeywordDto> getAIKeywordAnalysis(int userNo) {
		List<AnalysisKeywordDto> keywordStats = new ArrayList<>();

		// 사용자의 플레이리스트 조회
		List<PlaylistEntity> playlists = playlistRepository.findByUserUserNo(userNo);

		// 플레이리스트에 속한 노래들의 SONG_AIKEYWORD 통계 계산
		Map<String, Integer> keywordCountMap = new HashMap<>();
		// 모든 플레이리스트 조회하며 SONG_AIKEYWORD 통계 계산
		for (PlaylistEntity playlist : playlists) {
			for (ManageSongEntity manageSong : playlist.getManageSongs()) {
				SongEntity song = manageSong.getSong();
				String keyword = song.getSongAikeyword();
				
				// SONG_AIKEYWORD 존재하는 경우 통계에 반영
				if (keyword != null) {
					keywordCountMap.put(keyword, keywordCountMap.getOrDefault(keyword, 0) + 1);
				}
			}
		}

		// 통계 결과를 AnalysisKeywordDto로 변환하여 리스트에 추가
		for (Map.Entry<String, Integer> entry : keywordCountMap.entrySet()) {
			String keyword = entry.getKey();
			int count = entry.getValue();
			keywordStats.add(new AnalysisKeywordDto(keyword, count, count));
		}

		// 통계 결과를 빈도수(count) 기준으로 내림차순 정렬
		keywordStats.sort(Comparator.comparingInt(AnalysisKeywordDto::getCount).reversed());

		// 상위 3개만 선택
		List<AnalysisKeywordDto> top3KeywordStats = keywordStats.stream().limit(3).collect(Collectors.toList());

		// 상위 3개의 빈도수 합 계산
		int totalCount = top3KeywordStats.stream().mapToInt(AnalysisKeywordDto::getCount).sum();

		// 상위 3개의 비율 계산 및 설정
		for (AnalysisKeywordDto keywordStat : top3KeywordStats) {
			double ratio = (double) keywordStat.getCount() / totalCount * 100;
			// 소수점 첫째 자리까지 반올림
			ratio = Math.round(ratio * 10) / 10.0; 
			keywordStat.setRatio(ratio);
		}
		
		// 상위 3개의 통계 결과를 반환
		return top3KeywordStats;
	}
	
	// NULL인 AiKeyword 업데이트
	public void updateSongAiKeyword() {
        List<SongEntity> songs = songRepository.findBySongAikeywordIsNull();
        
        for (SongEntity song : songs) {
        	try {
        		// 10초 sleep
				Thread.sleep(10000);
				String songInfo = song.getSongTitle() + "-" + song.getSongArtist();
				List<String> AIKeyword = analysisApi.suggestedAIKeyword(new String[] { songInfo });
				
				if (!AIKeyword.isEmpty()) {
				    song.setSongAikeyword(AIKeyword.get(0));
				    songRepository.save(song);
				}
			} catch (JsonProcessingException | InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
	
}
