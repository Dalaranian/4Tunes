package com.multi.fourtunes.model.dao;

public interface KeywordDao {

	String[] getAllList();

	public int insertKeyword(String keyword, String userId);

}
