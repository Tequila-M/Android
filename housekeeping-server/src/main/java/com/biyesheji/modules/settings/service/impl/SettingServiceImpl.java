package com.biyesheji.modules.settings.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.biyesheji.modules.settings.dao.SettingDao;
import com.biyesheji.modules.settings.entity.SettingEntity;
import com.biyesheji.modules.settings.service.SettingService;



@Service("settingService")
public class SettingServiceImpl implements SettingService {
	@Autowired
	private SettingDao settingDao;
	
	@Override
	public SettingEntity queryObject(Integer id){
		return settingDao.queryObject(id);
	}
	
	@Override
	public List<SettingEntity> queryList(Map<String, Object> map){
		return settingDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return settingDao.queryTotal(map);
	}
	
	@Override
	public void save(SettingEntity setting){
		settingDao.save(setting);
	}
	
	@Override
	public void update(SettingEntity setting){
		settingDao.update(setting);
	}
	
	@Override
	public void delete(Integer id){
		settingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		settingDao.deleteBatch(ids);
	}
	
}
