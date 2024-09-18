package com.biyesheji.modules.settings.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biyesheji.common.annotation.AuthIgnore;
import com.biyesheji.common.utils.DateUtils;
import com.biyesheji.common.utils.R;
import com.biyesheji.modules.settings.entity.TimeEntity;
import com.biyesheji.modules.settings.service.TimeService;

@RestController
@RequestMapping("/api/time")
public class ApiTimeController {
	
	@Autowired
	private TimeService timeService;
	
	@AuthIgnore
	@GetMapping("list")
    public R list(@RequestParam Map<String, Object> map){
    	List<TimeEntity> timeList = timeService.queryList(map);
    	if(timeList == null || timeList.size() == 0) {
    		timeService.init(DateUtils.parseDate(map.get("appointDate").toString()), Integer.parseInt(map.get("teacherId").toString()));
    		timeList = timeService.queryList(map);
    	}
        return R.ok().put("timeList", timeList);
    }
}
