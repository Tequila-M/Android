package com.biyesheji.modules.settings.entity;

import java.io.Serializable;

import com.biyesheji.modules.project.entity.TeacherEntity;


/**
 * 预约设置
 * 
 * *
 * *
 */
public class SettingEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//医院id
	private Integer teacherId;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	//预约人数
	private Integer maxPeople;
	
	private TeacherEntity teacher;

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
	 * 设置：医院id
	 */
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	/**
	 * 获取：医院id
	 */
	public Integer getTeacherId() {
		return teacherId;
	}
	/**
	 * 设置：开始时间
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：开始时间
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 设置：结束时间
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：结束时间
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置：预约人数
	 */
	public void setMaxPeople(Integer maxPeople) {
		this.maxPeople = maxPeople;
	}
	/**
	 * 获取：预约人数
	 */
	public Integer getMaxPeople() {
		return maxPeople;
	}
	
	public TeacherEntity getTeacher() {
		return teacher;
	}
	
	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}
	
}
