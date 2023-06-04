package com.multi.fourtunes.model.biz;

public interface LoginBiz {
    boolean checkUserExist(String email, String userId);

    String[] getKeyword();
}