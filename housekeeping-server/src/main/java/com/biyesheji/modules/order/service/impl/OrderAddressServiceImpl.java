package com.biyesheji.modules.order.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biyesheji.modules.order.dao.OrderAddressDao;
import com.biyesheji.modules.order.entity.OrderAddressEntity;
import com.biyesheji.modules.order.service.OrderAddressService;


@Service("orderAddressService")
public class OrderAddressServiceImpl implements OrderAddressService {
	@Autowired
	private OrderAddressDao orderAddressDao;
	
	@Override
	public OrderAddressEntity queryObject(Integer id){
		return orderAddressDao.queryObject(id);
	}
	
	@Override
	public List<OrderAddressEntity> queryList(Map<String, Object> map){
		return orderAddressDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return orderAddressDao.queryTotal(map);
	}
	
	@Override
	public void save(OrderAddressEntity orderAddress){
		orderAddressDao.save(orderAddress);
	}
	
	@Override
	public void update(OrderAddressEntity orderAddress){
		orderAddressDao.update(orderAddress);
	}
	
	@Override
	public void delete(Integer id){
		orderAddressDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		orderAddressDao.deleteBatch(ids);
	}
	
}
