package com.biyesheji.modules.project.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biyesheji.modules.project.entity.TeacherEntity;
import com.biyesheji.modules.project.service.TeacherService;
import com.biyesheji.common.utils.Query;
import com.biyesheji.common.utils.R;


/**
 * 公司
 * 
 * *
 * *
 */
@RestController
@RequestMapping("teacher")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	@RequestMapping("/listAll")
	public R listAll(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TeacherEntity> teacherList = teacherService.queryList(query);
		
		return R.ok().put("teacherList", teacherList);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TeacherEntity> teacherList = teacherService.queryList(query);
		int total = teacherService.queryTotal(query);
		
		return R.ok().put("rows", teacherList).put("total", total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id){
		TeacherEntity teacher = teacherService.queryObject(id);
		
		return R.ok().put("teacher", teacher);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody TeacherEntity teacher){
		teacher.setCreateTime(new Date());
		teacherService.save(teacher);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody TeacherEntity teacher){
		teacherService.update(teacher);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Integer[] ids){
		teacherService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
