package com.biyesheji.modules.order.service;

import java.util.List;
import java.util.Map;

import com.biyesheji.modules.order.entity.OrderAddressEntity;

/**
 * 订单配送表
 * 
 * *
 * *
 */
public interface OrderAddressService {
	
	OrderAddressEntity queryObject(Integer id);
	
	List<OrderAddressEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(OrderAddressEntity orderAddress);
	
	void update(OrderAddressEntity orderAddress);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
