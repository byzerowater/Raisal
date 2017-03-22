package com.fourground.raisal.common.dto;

public class BaseVo extends BaseModel {

	private static final long serialVersionUID = 6309034240338791084L;
	
	private String modDtm;
	private String modId;
	private String regDtm;
	private String regId;
	
	public String getModDtm() {
		return modDtm;
	}
	public void setModDtm(String modDtm) {
		this.modDtm = modDtm;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
}
