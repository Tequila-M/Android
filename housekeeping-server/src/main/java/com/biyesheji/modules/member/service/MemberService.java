package com.biyesheji.modules.member.service;

import java.util.List;
import java.util.Map;

import com.biyesheji.modules.member.entity.MemberEntity;

/**
 * 会员
 * 
 * *
 * *
 */
public interface MemberService {
	
	MemberEntity queryObject(Long id);
	
	List<MemberEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MemberEntity member);
	
	void update(MemberEntity member);
	
	void delete(Long id);
	
	void deleteBatch(Integer[] ids);

	MemberEntity queryByLoginName(String loginName);

	void updatePwd(Long id, String password);
}
