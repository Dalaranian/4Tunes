package com.multi.fourtunes.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleManagerMapper {
    @Insert(" INSERT INTO ROLE_MANAGE VALUES(2, #{userNo} )")
    void insertUserRole(int userNo);

    @Select(" SELECT UR.ROLE_NAME FROM USER U JOIN ROLE_MANAGE RM ON U.USER_NO = RM.USER_NO JOIN USER_ROLE UR ON RM.ROLE_NO = UR.ROLE_NO WHERE U.USER_NO = #{userNo} ")
    String getUserRoleByUserNo(int userNo);
}
