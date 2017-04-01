package com.fourground.raisal.user.dto;

public class MyChnlInfoVo {

	private String userId;		//채널사에서 내려받은 접속자 고유값
	private String channelCode;	//채널사코드 (F, G)
	private String email;
	private String nickName;
	private String regAppCount;
	
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
