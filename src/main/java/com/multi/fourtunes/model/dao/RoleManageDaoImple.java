package com.multi.fourtunes.model.dao;

import com.multi.fourtunes.model.mapper.RoleManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleManageDaoImple implements RoleManageDao {

    @Autowired
    RoleManagerMapper mapper;
    @Override
    public void insertUserRole(int userNo) {
        mapper.insertUserRole(userNo);
    }
}
