package com.biyesheji.modules.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import com.biyesheji.modules.order.dao.OrderAddressDao;
import com.biyesheji.modules.order.dao.OrderDao;
import com.biyesheji.modules.order.entity.OrderEntity;
import com.biyesheji.modules.order.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderAddressDao orderAddressDao;
	
	@Override
	public OrderEntity queryObject(Integer id){
		return orderDao.queryObject(id);
	}
	
	@Override
	public List<OrderEntity> queryList(Map<String, Object> map){
		return orderDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return orderDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(OrderEntity order){
		orderDao.save(order);
		order.getOrderAddress().setOrderId(order.getId());
		orderAddressDao.save(order.getOrderAddress());
	}
	
	@Override
	public void update(OrderEntity order){
		orderDao.update(order);
	}
	
	@Override
	public void delete(Integer id){
		orderDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		orderDao.deleteBatch(ids);
	}

	@Override
	public void complete(Integer[] ids) {
		for (Integer id : ids) {
			OrderEntity order = new OrderEntity();
			order.setId(id);
			order.setStatus(3);
			orderDao.update(order);
		}
	}

	@Override
	public List<Map<String, String>> queryOrderCount() {
		return orderDao.queryOrderCount();
	}
	
}
