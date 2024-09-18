package com.biyesheji.modules.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biyesheji.modules.order.dao.OrderDao;
import com.biyesheji.modules.order.dao.EvaluationDao;
import com.biyesheji.modules.order.entity.OrderEntity;
import com.biyesheji.modules.order.entity.EvaluationEntity;
import com.biyesheji.modules.order.service.EvaluationService;


@Service("evaluationService")
public class EvaluationServiceImpl implements EvaluationService {
	@Autowired
	private EvaluationDao evaluationDao;
	@Autowired
	private OrderDao appointDao;
	
	@Override
	public EvaluationEntity queryObject(Integer id){
		return evaluationDao.queryObject(id);
	}
	
	@Override
	public List<EvaluationEntity> queryList(Map<String, Object> map){
		return evaluationDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return evaluationDao.queryTotal(map);
	}
	
	@Override
	public void save(EvaluationEntity evaluation){
		OrderEntity appoint = new OrderEntity();
		if(evaluation.getOrderId() != null) {
			appoint.setStatus(4);
			appoint.setId(evaluation.getOrderId());
			appointDao.update(appoint);
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("orderId", evaluation.getOrderId());
	    	appoint = appointDao.queryObject(evaluation.getOrderId());
	    	evaluation.setProjectId(appoint.getProjectId());
	    	evaluationDao.save(evaluation);
		}else {
			evaluationDao.save(evaluation);
		}
	}
	
	@Override
	public void update(EvaluationEntity evaluation){
		evaluationDao.update(evaluation);
	}
	
	@Override
	public void delete(Integer id){
		evaluationDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		evaluationDao.deleteBatch(ids);
	}
	
}
