package com.fourground.raisal.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestBodyVo implements Serializable {

	private static final long serialVersionUID = 4929224876278625649L;

	private Map<String, Object> body = null;
	
	// getUserAuthKey
	private String userUid;
	private String channelCode;
	private String email;
	
	// insertAppraisalInfo
	private String appName;
	private String title;
	private String reqTerm;
	private String appDesc;
	private List<Map<String, Object>> downInfo;
	
	private String useTerm;
	private String platformCode;
	private Map<String, Object> appElement;
	private String comment;
	
	private String userNm;

	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> body) {
		this.body = body;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReqTerm() {
		return reqTerm;
	}

	public void setReqTerm(String reqTerm) {
		this.reqTerm = reqTerm;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public List<Map<String, Object>> getDownInfo() {
		return downInfo;
	}

	public void setDownInfo(List<Map<String, Object>> downInfo) {
		this.downInfo = downInfo;
	}

	public String getUseTerm() {
		return useTerm;
	}

	public void setUseTerm(String useTerm) {
		this.useTerm = useTerm;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public Map<String, Object> getAppElement() {
		return appElement;
	}

	public void setAppElement(Map<String, Object> appElement) {
		this.appElement = appElement;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> convertToMap() {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> tempMap = new HashMap<String, Object>();

		ObjectMapper objMapper = new ObjectMapper();
		tempMap = objMapper.convertValue(this, Map.class);
		
		if(tempMap.get("body") != null) {
			return this.getBody();
		} else {
			Set<String> mapKey = tempMap.keySet();
			for(String key : mapKey) {
				Object objValue = tempMap.get(key);
				if(objValue != null) {
					rtnMap.put(key, objValue);
				}
			}
			return rtnMap;
		}
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
}