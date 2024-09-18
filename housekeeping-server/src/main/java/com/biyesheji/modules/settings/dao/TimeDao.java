package com.biyesheji.modules.settings.dao;

import com.biyesheji.modules.settings.entity.TimeEntity;
import com.biyesheji.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约时间
 * 
 * *
 * *
 */
@Mapper
public interface TimeDao extends BaseDao<TimeEntity> {

	void delPeople(Integer timeId);
	
	void addPeople(Integer timeId);
	
}
