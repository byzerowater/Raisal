package com.fourground.raisal.app.dto;

import com.fourground.raisal.common.dto.BaseVo;

public class AppInfoDetailVo extends BaseVo {

	private static final long serialVersionUID = 2128965615579589224L;
	
	private String appDesc;
	private String appDownloadUrl;
	
	private AppInfoVo appInfo;
	
	private RaisalElement appElement;

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

	public AppInfoVo getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppInfoVo appInfo) {
		this.appInfo = appInfo;
	}

	public RaisalElement getAppElement() {
		return appElement;
	}

	public void setAppElement(RaisalElement appElement) {
		this.appElement = appElement;
	}
}
