package com.multi.fourtunes.model.biz;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.fourtunes.model.dao.CommunityDao;
import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommentReportDto;
import com.multi.fourtunes.model.dto.CommunityDto;
import com.multi.fourtunes.model.dto.CommunityReportDto;

@Slf4j
@Service
public class CommunityBizImpl implements CommunityBiz {

	@Autowired
	private CommunityDao communityDao;

	@Override
	@Transactional
	public List<CommunityDto> getAll() {
		// // log.info("Biz getAll 진입");
		return communityDao.getAll();
	}

	@Override
	public CommunityDto get(int boardNo) {
		return communityDao.get(boardNo);
	}

	@Override
	public void insert(CommunityDto community) {
		communityDao.insert(community);

	}

	@Override
	public void update(CommunityDto community) {
		communityDao.update(community);

	}

	@Override
	public void delete(int boardNo) {
		communityDao.delete(boardNo);

	}

	@Override
	public void incrementViewCount(int boardNo) {
		communityDao.incrementViewCount(boardNo);
	}

	@Override
	public List<CommentDto> getComments(int boardNo) {
		return communityDao.getComments(boardNo);
	}

	@Override
	public void addComment(CommentDto comment) {
		communityDao.addComment(comment);
	}

	@Override
	public void deleteByBoardNo(int boardNo) {
		communityDao.deleteByBoardNo(boardNo);

	}

	@Override
	public CommentDto getComment(int commentNo) {
		return communityDao.getComment(commentNo);
	}

	@Override
	public void deleteComment(int commentNo) {
		communityDao.deleteComment(commentNo);

	}

	@Override
	public boolean isReported(int user_no, int boardNo) {
		int reportCount = communityDao.isReported(user_no, boardNo);
		return reportCount > 0;
	}

	@Override
	public void incrementReportCount(int boardNo) {
		communityDao.incrementReportCount(boardNo);

	}

	@Override
	public void reportCommunity(CommunityReportDto reportDto) {
		communityDao.reportCommunity(reportDto);

	}

	@Override
	public boolean isCommentReported(int user_no, int commentNo) {
		int reportCount = communityDao.isCommentReported(user_no, commentNo);
		return reportCount > 0;
	}

	@Override
	public void incrementCommentReportCount(int commentNo) {
		communityDao.incrementCommentReportCount(commentNo);

	}

	@Override
	public void reportComment(CommentReportDto reportDto) {
		communityDao.reportComment(reportDto);

	}


  
}
