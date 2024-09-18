package com.biyesheji.modules.settings.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biyesheji.modules.settings.entity.TimeEntity;
import com.biyesheji.modules.settings.service.TimeService;
import com.biyesheji.common.utils.Query;
import com.biyesheji.common.utils.R;




/**
 * 预约时间
 * 
 * *
 * *
 */
@RestController
@RequestMapping("time")
public class TimeController {
	@Autowired
	private TimeService timeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TimeEntity> timeList = timeService.queryList(query);
		int total = timeService.queryTotal(query);
		
		return R.ok().put("rows", timeList).put("total", total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id){
		TimeEntity time = timeService.queryObject(id);
		
		return R.ok().put("time", time);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody TimeEntity time){
		timeService.save(time);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody TimeEntity time){
		timeService.update(time);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Integer[] ids){
		timeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
