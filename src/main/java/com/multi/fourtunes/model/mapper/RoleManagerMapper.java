package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleManagerMapper {
    @Insert(" INSERT INTO ROLE_MANAGE VALUES(2, #{userNo} )")
    void insertUserRole(int userNo);
}
