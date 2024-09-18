package com.biyesheji.modules.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.biyesheji.modules.member.entity.MemberEntity;
import com.biyesheji.modules.sys.dao.BaseDao;


/**
 * 会员
 * 
 * *
 * *
 */
@Mapper
public interface MemberDao extends BaseDao<MemberEntity> {

	MemberEntity queryByLoginName(String loginName);

	void updatePwd(@Param("id")Long id, @Param("password")String password);
}
