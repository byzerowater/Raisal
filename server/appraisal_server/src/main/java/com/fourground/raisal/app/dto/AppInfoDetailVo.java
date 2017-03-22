package com.fourground.raisal.app.dto;

import java.util.ArrayList;
import java.util.List;

public class AppInfoDetailVo {

	private String appDesc;
	private String appDownloadUrl;
	private String raisalStartDtm;
	private String raisalEndDtm;
	
	private AppInfoVo appInfoVo;
	
	private List<AppraisalVo> usersRaisalList;

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

	public List<AppraisalVo> getUsersRaisalList() {
		return usersRaisalList;
	}

	public void setUsersRaisalList(List<AppraisalVo> usersRaisalList) {
		this.usersRaisalList = usersRaisalList;
	}
	
	public void adduserRaisal(AppraisalVo appraisalVo) {
		if(this.usersRaisalList != null) {
			this.usersRaisalList = new ArrayList<AppraisalVo>();
		}
		this.usersRaisalList.add(appraisalVo);
	}
}
