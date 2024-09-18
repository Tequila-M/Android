package com.biyesheji.modules.order.dao;

import com.biyesheji.modules.order.entity.OrderEntity;
import com.biyesheji.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 预约
 * 
 * *
 * *
 */
@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {

	List<Map<String, String>> queryOrderCount();
	
}
