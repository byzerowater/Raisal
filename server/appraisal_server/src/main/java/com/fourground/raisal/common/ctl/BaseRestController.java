package com.fourground.raisal.common.ctl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseRestController {
	
	protected final String BR = "<BR>";
	
	protected ResponseEntity<Object> success(Object data) {
//		RestResult restResult = new RestResult();
		return new ResponseEntity<Object>(data, HttpStatus.OK);
	}
	
	protected ResponseEntity<Object> fail(Object data, String errMsg) {
		return new ResponseEntity<Object>(data, HttpStatus.BAD_REQUEST);
	}
	
	protected ResponseEntity<Object> noContent(Object data, String errMsg) {
		return new ResponseEntity<Object>(data, HttpStatus.NO_CONTENT);
	}
}
