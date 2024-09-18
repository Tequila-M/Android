package com.biyesheji.modules.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.biyesheji.modules.member.entity.MemberEntity;
import com.biyesheji.modules.project.entity.ProjectEntity;
import com.biyesheji.modules.project.entity.TeacherEntity;


/**
 * 预约订单
 * 
 * *
 * *
 */
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	// 订单编号
	private String orderNumber;
	//项目
	private Integer projectId;
	//用户
	private Long userId;
	//预约时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date appointTime;
	//创建时间
	private Date createTime;
	
	private ProjectEntity project;
	
	private MemberEntity user;
	
	private Integer status;
	
	private Integer teacherId;
	
	private TeacherEntity teacher;
	
	private BigDecimal totalAmount;
	
	private OrderAddressEntity orderAddress;
	
	private String projectName;
	
	private String picUrl;
	
	private String teacherName;
	
	private Integer timeId;
	
	private String time;
	
	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：项目
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目
	 */
	public Integer getProjectId() {
		return projectId;
	}
	/**
	 * 设置：用户
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：预约时间
	 */
	public void setAppointTime(Date appointTime) {
		this.appointTime = appointTime;
	}
	/**
	 * 获取：预约时间
	 */
	public Date getAppointTime() {
		return appointTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	public ProjectEntity getProject() {
		return project;
	}
	
	public void setProject(ProjectEntity project) {
		this.project = project;
	}
	
	public MemberEntity getUser() {
		return user;
	}
	
	public void setUser(MemberEntity user) {
		this.user = user;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getTeacherId() {
		return teacherId;
	}
	
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	
	public TeacherEntity getTeacher() {
		return teacher;
	}
	
	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public OrderAddressEntity getOrderAddress() {
		return orderAddress;
	}
	
	public void setOrderAddress(OrderAddressEntity orderAddress) {
		this.orderAddress = orderAddress;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Integer getTimeId() {
		return timeId;
	}
	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
