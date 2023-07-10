package com.multi.fourtunes.model.biz;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.fourtunes.model.apis.AnalysisApi;
import com.multi.fourtunes.model.apis.YoutubeApi;
import com.multi.fourtunes.model.dao.AdminpageDao;
import com.multi.fourtunes.model.dao.ReportDao;
import com.multi.fourtunes.model.dao.UserDao;
import com.multi.fourtunes.model.dto.AdminCommentReportDto;
import com.multi.fourtunes.model.dto.AdminCommunityReportDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;

@Slf4j
@Service
public class AdminpageBizImpl implements AdminpageBiz{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	SongRepository songRepository;
	
	@Autowired
	YoutubeApi youtubeApi;
	
	@Autowired
	AdminpageDao adminDao;
	
	@Autowired
	AnalysisApi analysisApi;
	
	@Override
	public List<UserDto> selectList() {
		return userDao.selectList();
	}

	@Override
	public String selectGrade(int user_no) {
		return userDao.selectGrade(user_no);
	}

	@Override
	public int updateGradePaid(int user_no) {
		return userDao.updateGradePaid(user_no);
	}

	@Override
	public int updateGradeFree(int user_no) {
		return userDao.updateGradeFree(user_no);
	}

	@Override
	public int deleteUser(int user_no) {
		return userDao.deleteUser(user_no);
	}

	@Override
	public List<UserDto> searchUser(String name) {
		return userDao.searchUser(name);
	}

	@Override
	public List<AdminCommunityReportDto> selectReport() {
		return reportDao.selectReport();
	}

	@Override
	public int confirmReport(int board_no) {
		return reportDao.confirmReport(board_no);
	}

	@Override
	public int deleteReport(int board_no) {
		return reportDao.deleteReport(board_no);
	}

	@Override
	public List<AdminCommentReportDto> selectReportComment() {
		return reportDao.selectReportComment();
	}

	@Override
	public int confirmReportComment(int comment_no) {
		return reportDao.confirmReportComment(comment_no);
	}

	@Override
	public int deleteReportComment(int comment_no) {
		return reportDao.deleteReportComment(comment_no);
	}

	@Override
	public ArrayList<SongDto> setSonglink(ArrayList<SongDto> searchResult, String title) {
		
		ArrayList<SongDto> filterRes = new ArrayList<>();
		ArrayList<SongDto> finalRes = new ArrayList<>();
		
		for(SongDto res : searchResult) {
			// 검색 결과 10개를 하나씩 꺼내어, DB에 이미 저장되어있는 노래인지 확인 과정 거침 (JPA 활용)
			SongEntity songEntitiy = songRepository.findBySongId(res.getSongId());
			log.info("songEntity: " + songEntitiy);
			
			// DB에 이미 있는 노래이면, DB에 저장되어있는 youtubeLink 저장
			if(songEntitiy != null) {  
				res.setSongLink(songEntitiy.getSongLink());
				filterRes.add(res);
			}
			else if (res.getSongTitle().replace(" ", "").toUpperCase().contains(title.replace(" ", "").toUpperCase())) {
				try {
					res.setSongLink(youtubeApi.embedLinkGetter(res.getSongArtist(), res.getSongTitle()));
					// embedLinkGetter(res.getSongArtist(), res.getSongTitle())
					// testLinkGetter()
					filterRes.add(res);
				} catch (HttpClientErrorException e) {
					e.printStackTrace();
//					throw new RuntimeException(e);
				}
			}
		}
		
		try {
			// 한 노래에 대해 여러 검색결과가 있는 경우, 첫번째 검색결과만 finalRes에 저장
			//log.info("걸러진 친구들 은 \n"+filterRes);
			//finalRes.add(filterRes.get(0));
		} catch (java.lang.IndexOutOfBoundsException e) {
			log.info(title + "에 관한 결과 없음");
		}
		log.info("**최종 결과: " + filterRes);
		return filterRes;
	}

	@Override
	public String insertSong(String dto, String playlist) {
		// 결과를 리턴할 res 선언
		String res = null;
		
		try {  // String 타입의 dto를 SongDto 타입으로 변환
			ObjectMapper objMapper = new ObjectMapper();
			JsonNode json = objMapper.readTree(dto);
			
			SongDto songDto = new SongDto();
			songDto.setSongTitle(json.get("songTitle").asText());
			songDto.setSongArtist(json.get("songArtist").asText());
			songDto.setSongLink(json.get("songLink").asText());
			songDto.setSongId(json.get("songId").asText());
			songDto.setSongAlbumArt(json.get("songAlbumArt").asText());
			
			// 해당 노래가 DB에 있는지 확인
			SongEntity songEntity = songRepository.findBySongId(songDto.getSongId());
			
			// DB에 해당 노래가 없으면, DB에 먼저 저장
			if(songEntity == null) {
				SongEntity song = new SongEntity();
				song.setSongTitle(songDto.getSongTitle());
				song.setSongArtist(songDto.getSongArtist());
				song.setSongLink(songDto.getSongLink());
				song.setSongId(songDto.getSongId());
				song.setSongAlbumart(songDto.getSongAlbumArt());
				
//				// AIKeyword 넣는 부분
//				// 저장하려는 노래의 정보 가져오기
//				String songInfo = songDto.getSongTitle() + "-" + songDto.getSongArtist();
//				// AI 키워드 추출
//				List<String> AIKeyword = analysisApi.suggestedAIKeyword(new String[] { songInfo });
//				if (!AIKeyword.isEmpty()) {
//					// 첫 번째 추출된 키워드를 선택하여 저장
//					song.setSongAikeyword(AIKeyword.get(0));
//				
//				}
		
				songRepository.save(song);	
			} 
			
			songEntity = songRepository.findBySongId(songDto.getSongId());
			
			// 관리자가 선택한 플레이리스트에 해당 노래 저장
			adminDao.insertSong(songEntity.getSongNo(), playlist);
			
			res = playlist.toUpperCase() + " 플레이리스트에 추가되었습니다. ";
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (DuplicateKeyException e) {
            res = playlist.toUpperCase() + " 플레이리스트에 이미 저장된 노래입니다. ";
        } catch(DataIntegrityViolationException e) {
        	res = " 플레이리스트를 선택해주세요. ";
        } catch (Exception e){
        	e.printStackTrace();
            res = " 플레이리스트 저장에 실패했습니다. 다시 시도해주세요. ";
        }
		
		return res;
	}

}
