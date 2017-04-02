package com.fourground.raisal.app.dto;

public class AppInfoDetailVo {

	private String appDesc;
	private String appDownloadUrl;
	private String startDtm;
	private String endDtm;
	
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

	public AppInfoVo getAppInfoVo() {
		return appInfoVo;
	}

	public void setAppInfoVo(AppInfoVo appInfoVo) {
		this.appInfoVo = appInfoVo;
	}
}
