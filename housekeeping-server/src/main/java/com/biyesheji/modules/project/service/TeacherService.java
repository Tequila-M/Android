package com.biyesheji.modules.project.service;

import com.biyesheji.modules.project.entity.TeacherEntity;

import java.util.List;
import java.util.Map;

/**
 * 公司
 * 
 * *
 * *
 * @date 2021-01-11 21:15:29
 */
public interface TeacherService {
	
	TeacherEntity queryObject(Integer id);
	
	List<TeacherEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TeacherEntity teacher);
	
	void update(TeacherEntity teacher);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
