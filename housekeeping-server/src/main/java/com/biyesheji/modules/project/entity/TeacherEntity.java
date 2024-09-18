package com.biyesheji.modules.project.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司
 * 
 * *
 * *
 */
public class TeacherEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Integer id;
	// 公司名称
	private String realName;
	// 电话
	private String mobile;
	// 创建时间
	private Date createTime;

	private String specialty;

	private String remark;
	
	private String picUrl;

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
	 * 设置：电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取：电话
	 */
	public String getMobile() {
		return mobile;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
