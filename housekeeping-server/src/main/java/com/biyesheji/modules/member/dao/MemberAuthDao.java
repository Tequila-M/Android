package com.biyesheji.modules.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.biyesheji.modules.member.entity.MemberAuthEntity;
import com.biyesheji.modules.sys.dao.BaseDao;

/**
 * 授权
 * 
 * *
 * *
 */
@Mapper
public interface MemberAuthDao extends BaseDao<MemberAuthEntity> {

	MemberAuthEntity queryByOpenid(String openid);
	
	MemberAuthEntity queryOne(@Param("memberId")Integer memberId, @Param("authType")String authType);
	
}
