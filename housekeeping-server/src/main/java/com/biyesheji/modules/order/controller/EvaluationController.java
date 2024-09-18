package com.biyesheji.modules.order.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biyesheji.common.utils.Query;
import com.biyesheji.common.utils.R;
import com.biyesheji.modules.order.entity.EvaluationEntity;
import com.biyesheji.modules.order.service.EvaluationService;


/**
 * 订单评价
 * 
 * *
 * *
 */
@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
	@Autowired
	private EvaluationService evaluationService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<EvaluationEntity> evaluationList = evaluationService.queryList(query);
		int total = evaluationService.queryTotal(query);
		
		return R.ok().put("rows", evaluationList).put("total", total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public R info(@PathVariable("id") Integer id){
		EvaluationEntity evaluation = evaluationService.queryObject(id);
		
		return R.ok().put("evaluation", evaluation);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody EvaluationEntity evaluation){
		evaluationService.save(evaluation);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public R update(@RequestBody EvaluationEntity evaluation){
		evaluationService.update(evaluation);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Integer[] ids){
		evaluationService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
