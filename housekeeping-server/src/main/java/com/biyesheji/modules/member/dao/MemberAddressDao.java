package com.biyesheji.modules.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.biyesheji.modules.member.entity.MemberAddressEntity;
import com.biyesheji.modules.sys.dao.BaseDao;

/**
 * 用户地址
 * 
 * *
 * *
 */
@Mapper
public interface MemberAddressDao extends BaseDao<MemberAddressEntity> {

	void updateByUserId(MemberAddressEntity t);
	
}
