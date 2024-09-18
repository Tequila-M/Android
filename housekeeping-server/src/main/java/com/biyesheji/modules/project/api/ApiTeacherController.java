package com.biyesheji.modules.project.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biyesheji.common.annotation.AuthIgnore;
import com.biyesheji.common.utils.R;
import com.biyesheji.modules.project.entity.TeacherEntity;
import com.biyesheji.modules.project.service.TeacherService;

@RestController
@RequestMapping("/api/teacher/")
public class ApiTeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
    @AuthIgnore
    @GetMapping("list")
    public R list(@RequestParam Map<String, Object> params){
    	List<TeacherEntity> teacherList = teacherService.queryList(params);
        return R.ok().put("teacherList", teacherList);
    }
    
    
    @AuthIgnore
    @GetMapping("detail")
    public R detail(Integer id){
    	TeacherEntity teacher = teacherService.queryObject(id);
        return R.ok().put("teacher", teacher);
    }
}
