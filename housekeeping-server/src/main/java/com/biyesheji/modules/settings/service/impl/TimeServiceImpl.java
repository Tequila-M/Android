package com.biyesheji.modules.settings.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.biyesheji.modules.settings.dao.SettingDao;
import com.biyesheji.modules.settings.dao.TimeDao;
import com.biyesheji.modules.settings.entity.SettingEntity;
import com.biyesheji.modules.settings.entity.TimeEntity;
import com.biyesheji.modules.settings.service.TimeService;



@Service("timeService")
public class TimeServiceImpl implements TimeService {
	@Autowired
	private TimeDao timeDao;
	@Autowired
	private SettingDao settingDao;
	
	@Override
	public TimeEntity queryObject(Integer id){
		return timeDao.queryObject(id);
	}
	
	@Override
	public List<TimeEntity> queryList(Map<String, Object> map){
		return timeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return timeDao.queryTotal(map);
	}
	
	@Override
	public void save(TimeEntity time){
		timeDao.save(time);
	}
	
	@Override
	public void update(TimeEntity time){
		timeDao.update(time);
	}
	
	@Override
	public void delete(Integer id){
		timeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		timeDao.deleteBatch(ids);
	}

	@Override
	public void init(Date appointDate, Integer teacherId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("teacherId", teacherId);
		List<SettingEntity> settingList = settingDao.queryList(params);
		for (SettingEntity setting : settingList) {
			TimeEntity time = new TimeEntity();
			time.setTeacherId(teacherId);
			time.setAppointDate(appointDate);
			time.setPeople(0);
			time.setMaxPeople(setting.getMaxPeople());
			time.setStartTime(setting.getStartTime());
			time.setEndTime(setting.getEndTime());
			time.setStatus(1);
			timeDao.save(time);
		}
	}
	
}
