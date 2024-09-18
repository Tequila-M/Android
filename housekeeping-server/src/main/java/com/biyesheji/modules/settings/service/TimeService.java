package com.biyesheji.modules.settings.service;

import com.biyesheji.modules.settings.entity.TimeEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约时间
 * 
 * *
 * *
 */
public interface TimeService {
	
	TimeEntity queryObject(Integer id);
	
	List<TimeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TimeEntity time);
	
	void update(TimeEntity time);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	void init(Date appointDate, Integer teacherId);
}
