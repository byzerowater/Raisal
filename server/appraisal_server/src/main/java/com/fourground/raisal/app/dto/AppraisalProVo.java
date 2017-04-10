package com.fourground.raisal.app.dto;

public class AppraisalProVo {

	private String appComment;
	private RaisalElement appElement;
	private String userName;
	private String targetOsCode;
	
	private AppInfoVo appInfo;
	
	public String getAppComment() {
		return appComment;
	}
	public void setAppComment(String appComment) {
		this.appComment = appComment;
	}
	public RaisalElement getAppElement() {
		return appElement;
	}
	public void setAppElement(RaisalElement appElement) {
		this.appElement = appElement;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTargetOsCode() {
		return targetOsCode;
	}
	public void setTargetOsCode(String targetOsCode) {
		this.targetOsCode = targetOsCode;
	}
	public AppInfoVo getAppInfo() {
		return appInfo;
	}
	public void setAppInfo(AppInfoVo appInfo) {
		this.appInfo = appInfo;
	}
}