package com.biyesheji.modules.order.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biyesheji.common.utils.DateUtils;
import com.biyesheji.common.utils.NumberUtil;
import com.biyesheji.common.utils.R;
import com.biyesheji.modules.order.entity.OrderEntity;
import com.biyesheji.modules.order.service.OrderService;
import com.biyesheji.modules.project.entity.ProjectEntity;
import com.biyesheji.modules.project.service.ProjectService;

@RestController
@RequestMapping("/api/order/")
public class ApiOrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProjectService projectService;
	
    @GetMapping("list")
    public R list(@RequestAttribute("userId") Long userId, @RequestParam Map<String, Object> params){
    	params.put("userId", userId);
    	List<OrderEntity> orderList = orderService.queryList(params);
        return R.ok().put("orderList", orderList);
    }
    
    /**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody OrderEntity order, @RequestAttribute("userId") Long userId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", DateUtils.format(order.getAppointTime()));
		Integer total = orderService.queryTotal(map);
		
		ProjectEntity project = projectService.queryObject(order.getProjectId());
		order.setOrderNumber(NumberUtil.getOrderNumber());
		order.setStatus(1);
		order.setCreateTime(new Date());
		order.setUserId(userId);
		orderService.save(order);
		
		return R.ok().put("id", order.getId());
	}
    
    
    @GetMapping("detail")
    public R detail(Integer id){
    	OrderEntity order = orderService.queryObject(id);
        return R.ok().put("order", order);
    }
    
    @GetMapping("pay")
    public R pay(Integer id){
    	OrderEntity order = new OrderEntity();
    	order.setId(id);
    	order.setStatus(2);
    	orderService.update(order);
        return R.ok();
    }
    
    @GetMapping("cancel")
    public R cancel(Integer id){
    	OrderEntity order = new OrderEntity();
    	order.setId(id);
    	order.setStatus(0);
    	orderService.update(order);
        return R.ok();
    }
    
    @GetMapping("complete")
    public R complete(Integer id){
    	OrderEntity order = new OrderEntity();
    	order.setId(id);
    	order.setStatus(3);
    	orderService.update(order);
        return R.ok();
    }
}
