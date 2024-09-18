package com.biyesheji.modules.project.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biyesheji.common.annotation.AuthIgnore;
import com.biyesheji.common.utils.Query;
import com.biyesheji.common.utils.R;
import com.biyesheji.modules.project.entity.ProjectEntity;
import com.biyesheji.modules.project.service.ProjectService;

@RestController
@RequestMapping("/api/project/")
public class ApiProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@AuthIgnore
    @GetMapping("list")
    public R list(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
    	List<ProjectEntity> projectList = projectService.queryList(query);
        return R.ok().put("projectList", projectList);
    }
    
	@AuthIgnore
    @GetMapping("detail")
    public R detail(Integer id){
    	ProjectEntity project = projectService.queryObject(id);
        return R.ok().put("project", project);
    }
}
