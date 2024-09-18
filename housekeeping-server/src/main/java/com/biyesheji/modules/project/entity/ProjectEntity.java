package com.biyesheji.modules.project.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 项目
 * 
 * *
 * *
 * @date 2020-12-01 11:32:48
 */
public class ProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//项目名称
	private String projectName;
	//价格
	private BigDecimal price;
	//备注
	private String remark;
	//创建时间
	private Date createTime;
	
	private String picUrl;
	
	private Integer categoryId;
	
	private CategoryEntity category;
	
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
	 * 设置：项目名称
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * 获取：项目名称
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
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
	
	public String getPicUrl() {
		return picUrl;
	}
	
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public CategoryEntity getCategory() {
		return category;
	}
	
	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
}
