package com.fourground.raisal.app.ctl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fourground.raisal.app.dto.AppInfoDetailVo;
import com.fourground.raisal.app.dto.AppInfoVo;
import com.fourground.raisal.common.ctl.BaseRestController;
import com.fourground.raisal.common.dto.RequestBodyVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Appraisal API Contoller")
@Controller
@RequestMapping(value="/api/raisal", produces= MediaType.APPLICATION_JSON_VALUE)
public class AppraisalRestController extends BaseRestController {

	@ApiOperation(value="평가앱 목록 조회"
			,notes="1>조회"
			,response=AppInfoVo.class)
	@RequestMapping(value="/select", method={RequestMethod.GET})
	public ResponseEntity<Object> selectAppraisalList()
	{
		AppInfoVo appInfoVo = new AppInfoVo();
		
		return success(appInfoVo);
	}
	
	
	
	@ApiOperation(value="평가앱 상세조회"
			,notes="1>상세조회"
			,response=AppInfoDetailVo.class)
	@RequestMapping(value="/get/{appId}", method={RequestMethod.GET})
	public ResponseEntity<Object> getUserAuthKey(
			@ApiParam(required=true, value="APP ID", name="appId") @PathVariable("appId") String appId)
	{
		AppInfoDetailVo appInfoDetailVo = new AppInfoDetailVo();
		
		return success(appInfoDetailVo);
	}
	
	
	
	@ApiOperation(value="앱평가 요청"
			,notes="1>평가 요청하기"
			,response=AppInfoVo.class)
	@RequestMapping(value="/regist", method={RequestMethod.POST})
	public ResponseEntity<Object> getUserAuthKey(
			HttpServletRequest request
			,@ApiParam(name="body"
					,value="\"appName\":\"앱이름\","+BR+"\"title\":\"앱평가 제목\""+BR+"\"reqTerm\":\"테스트기간\","+BR+"\"appDesc\":\"앱설명\","
							+BR+"\"downInfo\" : ["+BR+"{\"platformCode\":\"OS종류\",\"downUrl\":\"다운로드주소\"},"
							+BR+"{\"platformCode\":\"OS종류\",\"downUrl\":\"다운로드주소\"}],"+BR) @RequestBody RequestBodyVo requestBody)
	{
		AppInfoVo appInfoVo = new AppInfoVo();
		
		
		return success(appInfoVo);
	}
}
