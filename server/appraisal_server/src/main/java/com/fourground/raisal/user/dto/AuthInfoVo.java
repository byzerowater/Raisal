package com.fourground.raisal.user.dto;

import com.fourground.raisal.common.dto.BaseVo;

public class AuthInfoVo extends BaseVo {

	private static final long serialVersionUID = 8991289915375865481L;
	
	private String authKey;
	private String channelCode;
	private String email;
	private String nickName;
	private String regAppCount;
	private String userId;
	
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRegAppCount() {
		return regAppCount;
	}
	public void setRegAppCount(String regAppCount) {
		this.regAppCount = regAppCount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}