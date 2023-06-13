package com.multi.fourtunes.model.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.fourtunes.model.dao.CommunityDao;
import com.multi.fourtunes.model.dto.CommentDto;
import com.multi.fourtunes.model.dto.CommunityDto;

@Service
public class CommunityBizImpl implements CommunityBiz {

	@Autowired
	private CommunityDao communityDao;

	@Override
	@Transactional
	public List<CommunityDto> getAll() {
		// System.out.println("Biz getAll 진입");
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

	

//	@Override
//	public void updateReportCount(int boardNo) {
//		communityDao.updateReportCount(boardNo);
//	}
//
//	@Override
//	public int checkDuplicateReport(int boardNo, int userNo) {
//		return communityDao.checkDuplicateReport(boardNo, userNo);
//	}
}
