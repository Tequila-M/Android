package com.biyesheji.modules.member.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员
 * 
 * *
 * *
 */
public class MemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Long id;
	// 昵称
	private String nickname;
	// 头像
	private String avatarUrl;
	// 性别
	private String gender;
	//姓名
	private String realName;
	//电话
	private String mobile;
	//登录名
	private String loginName;
	//密码
	private String password;
	// 创建时间
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
