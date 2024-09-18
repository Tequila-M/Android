package com.wfuhui.housekeeping.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
public class Order {
    private Integer id;
    // 订单编号
    private String orderNumber;
    // 订单金额
    private BigDecimal totalAmount;
    // 订单状态，1：待付款，2：预约中，3：已完成
    private Integer status;
    // 创建时间
    private Date createTime;

    private OrderProject orderProject;

    private OrderShipment orderShipment;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public OrderProject getOrderProject() {
        return orderProject;
    }

    public void setOrderProject(OrderProject orderProject) {
        this.orderProject = orderProject;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public OrderShipment getOrderShipment() {
        return orderShipment;
    }

    public void setOrderShipment(OrderShipment orderShipment) {
        this.orderShipment = orderShipment;
    }
}
