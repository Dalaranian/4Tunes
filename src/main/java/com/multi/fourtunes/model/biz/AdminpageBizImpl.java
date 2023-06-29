package com.multi.fourtunes.model.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.multi.fourtunes.model.apis.YoutubeApi;
import com.multi.fourtunes.model.dao.ReportDao;
import com.multi.fourtunes.model.dao.UserDao;
import com.multi.fourtunes.model.dto.AdminCommentReportDto;
import com.multi.fourtunes.model.dto.AdminCommunityReportDto;
import com.multi.fourtunes.model.dto.SongDto;
import com.multi.fourtunes.model.dto.UserDto;
import com.multi.fourtunes.model.jpa.entity.SongEntity;
import com.multi.fourtunes.model.jpa.repository.SongRepository;

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
	public ArrayList<SongDto> setSonglink(ArrayList<SongDto> searchResult, String title, String artist) {
		
		ArrayList<SongDto> filterRes = new ArrayList<>();
		ArrayList<SongDto> finalRes = new ArrayList<>();
		
		for(SongDto res : searchResult) {
			// 검색 결과 10개를 하나씩 꺼내어, DB에 이미 저장되어있는 노래인지 확인 과정 거침 (JPA 활용)
			SongEntity songEntitiy = songRepository.findBySongId(res.getSongId());
		
			// DB에 이미 있는 노래이면, DB에 저장되어있는 youtubeLink 저장
			if(songEntitiy != null && res.getSongArtist().replace(" ", "").toUpperCase().contains(artist.replace(" ", "").toUpperCase())) {  
				res.setSongLink(songEntitiy.getSongLink());
				filterRes.add(res);
			}
			else if (res.getSongArtist().replace(" ", "").toUpperCase().contains(artist.replace(" ", "").toUpperCase())
					&& res.getSongTitle().replace(" ", "").toUpperCase().contains(title.replace(" ", "").toUpperCase())) {
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
			//System.out.println("걸러진 친구들 은 \n"+filterRes);
			finalRes.add(filterRes.get(0));
		} catch (java.lang.IndexOutOfBoundsException e) {
			System.out.println(title + "에 관한 결과 없음");
		}
		System.out.println("**최종 결과: " + finalRes);
		return filterRes;
	}



}
