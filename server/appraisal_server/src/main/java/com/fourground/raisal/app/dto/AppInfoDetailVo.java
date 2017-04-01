package com.fourground.raisal.app.dto;

public class AppInfoDetailVo {

	private String appDesc;
	private String appDownloadUrl;
	private String raisalStartDtm;
	private String raisalEndDtm;
	
	private AppInfoVo appInfoVo;

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppDownloadUrl() {
		return appDownloadUrl;
	}

	public void setAppDownloadUrl(String appDownloadUrl) {
		this.appDownloadUrl = appDownloadUrl;
	}

	public String getRaisalStartDtm() {
		return raisalStartDtm;
	}

	public void setRaisalStartDtm(String raisalStartDtm) {
		this.raisalStartDtm = raisalStartDtm;
	}

	public String getRaisalEndDtm() {
		return raisalEndDtm;
	}

	public void setRaisalEndDtm(String raisalEndDtm) {
		this.raisalEndDtm = raisalEndDtm;
	}

	public AppInfoVo getAppInfoVo() {
		return appInfoVo;
	}

	public void setAppInfoVo(AppInfoVo appInfoVo) {
		this.appInfoVo = appInfoVo;
	}
}
