package com.fourground.raisal.app.dto;

public class AppInfoVo {

	private String appId;
	private String appName;
//	private String title;
	private String targetOsCode;
	private String raisalStat;
	private int nPartyUserCount;
	
//	private AppraisalVo appraisalAvg;
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
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
	public String getTargetOsCode() {
		return targetOsCode;
	}
	public void setTargetOsCode(String targetOsCode) {
		this.targetOsCode = targetOsCode;
	}
	public String getRaisalStat() {
		return raisalStat;
	}
	public void setRaisalStat(String raisalStat) {
		this.raisalStat = raisalStat;
	}
	public int getnPartyUserCount() {
		return nPartyUserCount;
	}
	public void setnPartyUserCount(int nPartyUserCount) {
		this.nPartyUserCount = nPartyUserCount;
	}
//	public AppraisalVo getAppraisalAvg() {
//		return appraisalAvg;
//	}
//	public void setAppraisalAvg(AppraisalVo appraisalAvg) {
//		this.appraisalAvg = appraisalAvg;
//	}
	public String getAppraisalAvg() {
		return appraisalAvg;
	}
	public void setAppraisalAvg(String appraisalAvg) {
		this.appraisalAvg = appraisalAvg;
	}
}
