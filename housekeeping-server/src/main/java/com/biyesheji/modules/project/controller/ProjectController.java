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

import com.biyesheji.modules.project.entity.ProjectEntity;
import com.biyesheji.modules.project.service.ProjectService;
import com.biyesheji.common.utils.Query;
import com.biyesheji.common.utils.R;

/**
 * 项目
 * 
 * *
 * *
 */
@RestController
@RequestMapping("project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ProjectEntity> projectList = projectService.queryList(query);
		int total = projectService.queryTotal(query);
		
		return R.ok().put("rows", projectList).put("total", total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id){
		ProjectEntity project = projectService.queryObject(id);
		
		return R.ok().put("project", project);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody ProjectEntity project){
		project.setCreateTime(new Date());
		projectService.save(project);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody ProjectEntity project){
		projectService.update(project);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Integer[] ids){
		projectService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
