package com.biyesheji.modules.order.service;

import java.util.List;
import java.util.Map;

import com.biyesheji.modules.order.entity.EvaluationEntity;

/**
 * 订单评价
 * 
 * *
 * *
 */
public interface EvaluationService {
	
	EvaluationEntity queryObject(Integer id);
	
	List<EvaluationEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(EvaluationEntity evaluation);
	
	void update(EvaluationEntity evaluation);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
