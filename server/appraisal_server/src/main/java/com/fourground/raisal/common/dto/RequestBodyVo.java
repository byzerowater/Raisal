package com.fourground.raisal.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestBodyVo implements Serializable {

	private static final long serialVersionUID = 4929224876278625649L;

	private Map<String, Object> body = new HashMap<String, Object>();

	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> body) {
		this.body = body;
	}
}