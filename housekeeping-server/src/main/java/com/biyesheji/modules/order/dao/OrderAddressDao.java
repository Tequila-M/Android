package com.biyesheji.modules.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.biyesheji.modules.order.entity.OrderAddressEntity;
import com.biyesheji.modules.sys.dao.BaseDao;

/**
 * 订单配送表
 * 
 * *
 * *
 */
@Mapper
public interface OrderAddressDao extends BaseDao<OrderAddressEntity> {

	OrderAddressEntity queryByOrderId(Integer orderId);
	
}
