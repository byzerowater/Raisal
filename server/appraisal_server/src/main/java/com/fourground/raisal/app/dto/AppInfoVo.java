package com.fourground.raisal.app.dto;

public class AppInfoVo {

	private String appId;
	private String appName;
	private String targetOsCode;
	private String appStatus;
	private int nPartyUserCount;
	private String startDtm;
	private String endDtm;
	
	private String appraisalAvg;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getTargetOsCode() {
		return targetOsCode;
	}
	public void setTargetOsCode(String targetOsCode) {
		this.targetOsCode = targetOsCode;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public int getnPartyUserCount() {
		return nPartyUserCount;
	}
	public void setnPartyUserCount(int nPartyUserCount) {
		this.nPartyUserCount = nPartyUserCount;
	}
	public String getStartDtm() {
		return startDtm;
	}
	public void setStartDtm(String startDtm) {
		this.startDtm = startDtm;
	}
	public String getEndDtm() {
		return endDtm;
	}
	public void setEndDtm(String endDtm) {
		this.endDtm = endDtm;
	}
	public String getAppraisalAvg() {
		return appraisalAvg;
	}
	public void setAppraisalAvg(String appraisalAvg) {
		this.appraisalAvg = appraisalAvg;
	}
}
