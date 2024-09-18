package com.biyesheji.modules.order.service;

import com.biyesheji.modules.order.entity.OrderEntity;

import java.util.List;
import java.util.Map;

/**
 * 预约
 * 
 * *
 * *
 */
public interface OrderService {
	
	OrderEntity queryObject(Integer id);
	
	List<OrderEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(OrderEntity order);
	
	void update(OrderEntity order);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	void complete(Integer[] ids);

	List<Map<String, String>> queryOrderCount();
}
