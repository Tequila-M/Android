package com.biyesheji.modules.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.biyesheji.modules.project.dao.TeacherDao;
import com.biyesheji.modules.project.entity.TeacherEntity;
import com.biyesheji.modules.project.service.TeacherService;



@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private TeacherDao teacherDao;
	
	@Override
	public TeacherEntity queryObject(Integer id){
		return teacherDao.queryObject(id);
	}
	
	@Override
	public List<TeacherEntity> queryList(Map<String, Object> map){
		return teacherDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return teacherDao.queryTotal(map);
	}
	
	@Override
	public void save(TeacherEntity teacher){
		teacherDao.save(teacher);
	}
	
	@Override
	public void update(TeacherEntity teacher){
		teacherDao.update(teacher);
	}
	
	@Override
	public void delete(Integer id){
		teacherDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		teacherDao.deleteBatch(ids);
	}
	
}
