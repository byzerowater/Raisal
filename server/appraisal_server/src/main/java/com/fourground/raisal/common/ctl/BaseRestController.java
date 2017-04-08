package com.fourground.raisal.common.ctl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fourground.raisal.common.dto.RestResult;

public class BaseRestController {
	
	protected final String BR = "<BR>";
	
	protected ResponseEntity<Object> success(Object data) {
		return new ResponseEntity<Object>(data, HttpStatus.OK);
	}
	
	protected ResponseEntity<Object> successWithPage(Object data) {
		RestResult restResult = new RestResult();
		restResult.init(data);
		return new ResponseEntity<Object>(restResult, HttpStatus.OK);
	}	
	
	protected ResponseEntity<Object> dbFail(String errMsg) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("errorMsg", errMsg);
		return new ResponseEntity<Object>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	protected ResponseEntity<Object> fail(Object data, String errMsg) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("errorMsg", errMsg);
		return new ResponseEntity<Object>(rtnMap, HttpStatus.BAD_REQUEST);
	}
	
	protected ResponseEntity<Object> authFail(String errMsg) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("errorMsg", errMsg);
		return new ResponseEntity<Object>(errMsg, HttpStatus.UNAUTHORIZED);
	}

	protected ResponseEntity<Object> noContent(Object data, String errMsg) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("errorMsg", errMsg);
		return new ResponseEntity<Object>(errMsg, HttpStatus.NO_CONTENT);
	}
}
