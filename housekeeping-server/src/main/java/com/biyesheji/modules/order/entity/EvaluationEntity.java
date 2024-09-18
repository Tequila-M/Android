package com.biyesheji.modules.order.entity;

import java.io.Serializable;
import java.util.Date;

import com.biyesheji.modules.member.entity.MemberEntity;
import com.biyesheji.modules.project.entity.ProjectEntity;

/**
 * 订单评价
 * 
 * *
 * *
 */
public class EvaluationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 主键
	private Integer id;
	// 订单id
	private Integer orderId;
	// 用户id
	private Long memberId;
	// 评价内容
	private String content;
	//
	private Integer star;
	// 评价时间
	private Date createTime;
	// 项目id
	private Integer projectId;

	private MemberEntity member;
	
	private ProjectEntity project;

	/**
	 * 设置：主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取：主键
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置：订单id
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * 获取：订单id
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * 设置：用户id
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	/**
	 * 获取：用户id
	 */
	public Long getMemberId() {
		return memberId;
	}

	/**
	 * 设置：评价内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取：评价内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置：
	 */
	public void setStar(Integer star) {
		this.star = star;
	}

	/**
	 * 获取：
	 */
	public Integer getStar() {
		return star;
	}

	/**
	 * 设置：评价时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：评价时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}


}
