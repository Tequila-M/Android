package com.biyesheji.modules.project.service;

import com.biyesheji.modules.project.entity.ProjectEntity;

import java.util.List;
import java.util.Map;

/**
 * 项目
 * 
 * *
 * *
 * @date 2020-12-01 11:32:48
 */
public interface ProjectService {
	
	ProjectEntity queryObject(Integer id);
	
	List<ProjectEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProjectEntity project);
	
	void update(ProjectEntity project);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
