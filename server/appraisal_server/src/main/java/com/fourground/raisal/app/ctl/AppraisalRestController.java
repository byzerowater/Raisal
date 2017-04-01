package com.fourground.raisal.app.ctl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fourground.raisal.app.dto.AppInfoDetailVo;
import com.fourground.raisal.app.dto.AppInfoVo;
import com.fourground.raisal.app.dto.AppraisalVo;
import com.fourground.raisal.common.ctl.BaseRestController;
import com.fourground.raisal.common.dto.RequestBodyVo;
import com.fourground.raisal.common.dto.ResultVo;

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
	public ResponseEntity<Object> selectAppraisalList(
			@ApiParam(required=false, value="조회페이지", name="page") @RequestParam(value="page",required=false) Integer currPage
			,@ApiParam(required=false, value="페이지당조회수", name="size") @RequestParam(value="size",required=false) Integer size)
	{
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		List<AppInfoVo> appInfoVoList = new ArrayList<AppInfoVo>();
		
		resultData.put("data", appInfoVoList);
		resultData.put("baseUrl", "/api/raisal/select");
		resultData.put("totPages", 10);
		resultData.put("currPage", currPage);
		resultData.put("size", size);
		
		return successWithPage(resultData);
	}
	
	@ApiOperation(value="평가앱 상세 기본정보 조회"
			,notes="1>상세조회"
			,response=AppInfoDetailVo.class)
	@RequestMapping(value="/get/{appId}", method={RequestMethod.GET})
	public ResponseEntity<Object> getAppraisalDetail(
			@ApiParam(required=true, value="APP ID", name="appId") @PathVariable("appId") String appId)
	{
		AppInfoDetailVo appInfoDetailVo = new AppInfoDetailVo();
		
		return success(appInfoDetailVo);
	}
	
	@ApiOperation(value="평가앱 상세 평가내용 조회"
			,notes="1>상세조회"
			,response=AppraisalVo.class)
	@RequestMapping(value="/collect/{appId}", method={RequestMethod.GET})
	public ResponseEntity<Object> collectAppraisalList(
			@ApiParam(required=true, value="APP ID", name="appId") @PathVariable("appId") String appId
			,@ApiParam(required=false, value="조회페이지", name="page") @RequestParam(value="page",required=false) Integer currPage
			,@ApiParam(required=false, value="페이지당조회수", name="size") @RequestParam(value="size",required=false) Integer size)
	{
		Map<String, Object> resultData = new HashMap<String, Object>();
		List<AppraisalVo> collectAppraisalList = new ArrayList<AppraisalVo>();
		
		resultData.put("data", collectAppraisalList);
		resultData.put("baseUrl", "/api/raisal/collect/"+appId);
		resultData.put("totPages", 10);
		resultData.put("currPage", currPage);
		resultData.put("size", size);
		
		return successWithPage(resultData);
	}
	
	@ApiOperation(value="앱평가 등록"
			,notes="1>앱 등록하기"
			,response=ResultVo.class)
	@RequestMapping(value="/regist", method={RequestMethod.POST})
	public ResponseEntity<Object> insertAppraisalInfo(
			@ApiParam(name="body"
					,value="\"appName\":\"앱이름\","+BR+"\"title\":\"앱평가 제목\","+BR+"\"reqTerm\":\"테스트기간\","+BR+"\"appDesc\":\"앱설명\","
							+BR+"\"downInfo\" : ["+BR+"{\"platformCode\":\"OS종류(IOS,ADR)\",\"downUrl\":\"다운로드주소\"},"
							+BR+"{\"platformCode\":\"OS종류\",\"downUrl\":\"다운로드주소\"}]"+BR) @RequestBody RequestBodyVo requestBody)
	{
		ResultVo result = new ResultVo();
		
		result.setSuccess(true);
		result.setMessage("등록성공");
		result.setAppId("AP1234");
		
		return success(result);
	}
	
	@ApiOperation(value="앱평가하기"
			,notes="1>평가하기"
			,response=ResultVo.class)
	@RequestMapping(value="/vote/{appId}", method={RequestMethod.POST})
	public ResponseEntity<Object> insertAppAppraisal(
			@ApiParam(required=true, value="APP ID", name="appId") @PathVariable("appId") String appId
			,@ApiParam(name="body"
					,value="\"useTerm\":\"앱이용기간\","+BR+"\"platformCode\":\"OS종류(IOS,ADR)\","+BR
					+"\"raisalPoint\" : "+BR+"{\"contents\":\"콘텐츠점수(5)\",\"design\":\"디자인점수(5)\",\"satisfaction\":\"지속성(5)\",\"useful\":\"사용성(5)\"}"+BR
					+"\"comment\" : \"사용소감\""+BR) @RequestBody RequestBodyVo requestBody)
	{
		ResultVo result = new ResultVo();
		
		result.setSuccess(true);
		result.setMessage("평가완료");
		result.setAppId(appId);
		
		return success(result);
	}
}
