package com.fourground.raisal.app.dto;

public class AppraisalVo {

	private String raisalComment;
	private RaisalElement raisalPoint;
	private String userName;
	private String targetOsCode;
	
	public String getRaisalComment() {
		return raisalComment;
	}
	public void setRaisalComment(String raisalComment) {
		this.raisalComment = raisalComment;
	}
	public RaisalElement getRaisalPoint() {
		return raisalPoint;
	}
	public void setRaisalPoint(RaisalElement raisalPoint) {
		this.raisalPoint = raisalPoint;
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
}