package com.fourground.raisal.app.ctl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.fourground.raisal.app.svc.AppManagerService;
import com.fourground.raisal.common.Constants;
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
	
	@Autowired
	private AppManagerService appManagerService;

	@ApiOperation(value="평가앱 목록 조회"
			,notes="1>조회"
			,response=AppInfoVo.class)
	@RequestMapping(value="/select", method={RequestMethod.GET})
	public ResponseEntity<Object> selectAppraisalList(
			@ApiParam(required=false, value="조회페이지", name="page") @RequestParam(value="page",required=false) Integer reqPage
			,@ApiParam(required=false, value="페이지당조회수", name="size") @RequestParam(value="size",required=false) Integer size)
	{
		Map<String,Object> parameter = new HashMap<String, Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		int nListLength = size != null ? size.intValue() : Constants.Search.nMinSearchCnt;
		int nReqPage = reqPage != null && reqPage.intValue() > 0 ? reqPage - 1 : 0;
		
		int totPages = this.getCalcuTotPage(appManagerService.selectAppListCount(parameter)
				,nListLength);

		parameter.put("pagingYn", "Y");
		parameter.put("pagingOrder", "reg_dtm");
		parameter.put("pagingOffset", nReqPage * nListLength);
		parameter.put("length", nListLength);
		
		List<AppInfoVo> appInfoVoList = new ArrayList<AppInfoVo>();
		appInfoVoList = appManagerService.selectAppList(parameter);
		
		resultData.put("data", appInfoVoList);
		resultData.put("baseUrl", "/api/raisal/select");
		resultData.put("totPages", totPages);
		resultData.put("currPage", reqPage);
		resultData.put("size", size);
		
		return successWithPage(resultData);
	}
	
	@ApiOperation(value="내가 등록한 평가앱 목록 조회"
			,notes="1>조회"
			,response=AppInfoVo.class)
	@RequestMapping(value="/my/select", method={RequestMethod.GET})
	public ResponseEntity<Object> selectMyAppraisalList(
			HttpServletRequest request
			,@ApiParam(required=false, value="조회페이지", name="page") @RequestParam(value="page",required=false) Integer reqPage
			,@ApiParam(required=false, value="페이지당조회수", name="size") @RequestParam(value="size",required=false) Integer size)
	{
		Map<String,Object> parameter = new HashMap<String, Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();
		
		
		String authKey = request.getHeader("Authorization");
		// Searching my userid
		
		
		
		int nListLength = size != null ? size.intValue() : Constants.Search.nMinSearchCnt;
		int nReqPage = reqPage != null && reqPage.intValue() > 0 ? reqPage - 1 : 0;
		
		int totPages = this.getCalcuTotPage(appManagerService.selectAppListCount(parameter)
				,nListLength);

		parameter.put("pagingYn", "Y");
		parameter.put("pagingOrder", "reg_dtm");
		parameter.put("pagingOffset", nReqPage * nListLength);
		parameter.put("length", nListLength);
		
		List<AppInfoVo> appInfoVoList = new ArrayList<AppInfoVo>();
		appInfoVoList = appManagerService.selectAppList(parameter);
		
		resultData.put("data", appInfoVoList);
		resultData.put("baseUrl", "/api/raisal/my/select");
		resultData.put("totPages", totPages);
		resultData.put("currPage", reqPage);
		resultData.put("size", size);
		
		return successWithPage(resultData);
	}
	
	@ApiOperation(value="평가앱 상세 기본정보 조회"
			,notes="1>상세조회"
			,response=AppInfoDetailVo.class)
	@RequestMapping(value="/get/{appId}", method={RequestMethod.GET})
	public ResponseEntity<Object> getAppraisalDetail(
			HttpServletRequest request
			,@ApiParam(required=true, value="APP ID", name="appId") @PathVariable("appId") String appId)
	{
		Map<String,Object> parameter = new HashMap<String, Object>();
		parameter.put("appId", appId);
		
		if(request.getHeader("osType") != null && request.getHeader("osType").equals("ios")) {
			parameter.put("plfmCd", "IOS");
		} else {
			parameter.put("plfmCd", "ADR");
		}
		
		AppInfoDetailVo appInfoDetailVo = new AppInfoDetailVo();
		appInfoDetailVo = appManagerService.getAppDetail(parameter);
		
		return success(appInfoDetailVo);
	}
	
	@ApiOperation(value="평가앱 상세 평가내용 조회"
			,notes="1>상세조회"
			,response=AppraisalVo.class)
	@RequestMapping(value="/collect/{appId}", method={RequestMethod.GET})
	public ResponseEntity<Object> collectAppraisalList(
			HttpServletRequest request
			,@ApiParam(required=true, value="APP ID", name="appId") @PathVariable("appId") String appId
			,@ApiParam(required=false, value="조회페이지", name="page") @RequestParam(value="page",required=false) Integer reqPage
			,@ApiParam(required=false, value="페이지당조회수", name="size") @RequestParam(value="size",required=false) Integer size)
	{
		Map<String,Object> parameter = new HashMap<String, Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();

		int nListLength = size != null ? size.intValue() : Constants.Search.nMinSearchCnt;
		int nReqPage = reqPage != null && reqPage.intValue() > 0 ? reqPage - 1 : 0;
		
		parameter.put("appId", appId);
		
		int totPages = this.getCalcuTotPage(appManagerService.selectAppraisalListCount(parameter)
				,nListLength);

		parameter.put("pagingYn", "Y");
		parameter.put("pagingOrder", "aprs_id");
		parameter.put("pagingOffset", nReqPage * nListLength);
		parameter.put("length", nListLength);
		
//		if(request.getHeader("osType") != null && request.getHeader("osType").equals("ios")) {
//			parameter.put("plfmCd", "IOS");
//		} else {
//			parameter.put("plfmCd", "ADR");
//		}
		
		List<AppraisalVo> collectAppraisalList = new ArrayList<AppraisalVo>();
		collectAppraisalList = appManagerService.selectAppraisalList(parameter);
		
		resultData.put("data", collectAppraisalList);
		resultData.put("baseUrl", "/api/raisal/collect/"+appId);
		resultData.put("totPages", totPages);
		resultData.put("currPage", reqPage);
		resultData.put("size", size);
		
		return successWithPage(resultData);
	}
	
	@ApiOperation(value="앱평가 등록"
			,notes="1>앱 등록하기"
			,response=ResultVo.class)
	@RequestMapping(value="/regist", method={RequestMethod.POST})
	public ResponseEntity<Object> insertAppraisalInfo(
			HttpServletRequest request
			,@ApiParam(name="body"
					,value="\"appName\":\"앱이름\","+BR+"\"title\":\"앱평가 제목\","+BR+"\"reqTerm\":\"테스트기간\","+BR+"\"appDesc\":\"앱설명\","
							+BR+"\"downInfo\" : ["+BR+"{\"platformCode\":\"OS종류(IOS,ADR)\",\"downUrl\":\"다운로드주소\"},"
							+BR+"{\"platformCode\":\"OS종류\",\"downUrl\":\"다운로드주소\"}]"+BR) @RequestBody RequestBodyVo requestBody)
	{
		Map<String, Object> parameter = requestBody.convertToMap();
		ResultVo result = new ResultVo();
		
		// real
//		int nInsrtCount = appManagerService.registAppInfo(parameter, request.getHeader("Authorization"));
		// test
		String appId = "";
		
		try {
			String authKey = request.getHeader("Authorization");
			/* test */ authKey = "L9+BpDHrub+WsyPGL3Zp3k60jG5+ddMGIxrlBD6q/NLNZCvvdYGBNarY/eERG5C6";					
			appId = appManagerService.registAppInfo(parameter, authKey);
		} catch (Exception ex) {
			return authFail(ex.getMessage());
		}
		
		result.setSuccess(true);
		result.setMessage("등록성공");
		result.setAppId(appId);
		
		return success(result);
	}
	
	@ApiOperation(value="앱평가하기"
			,notes="1>평가하기"
			,response=ResultVo.class)
	@RequestMapping(value="/vote/{appId}", method={RequestMethod.POST})
	public ResponseEntity<Object> insertAppAppraisal(
			HttpServletRequest request
			,@ApiParam(required=true, value="APP ID", name="appId") @PathVariable("appId") String appId
			,@ApiParam(name="body"
					,value="\"useTerm\":\"앱이용기간\","+BR+"\"platformCode\":\"OS종류(IOS,ADR)\","+BR
					+"\"appElement\" : "+BR+"{\"contents\":\"콘텐츠점수(5)\",\"design\":\"디자인점수(5)\",\"satisfaction\":\"지속성(5)\",\"useful\":\"사용성(5)\"},"+BR
					+"\"comment\" : \"사용소감\""+BR) @RequestBody RequestBodyVo requestBody)
	{
		Map<String, Object> parameter = requestBody.convertToMap();
		ResultVo result = new ResultVo();
		
		try {
			String authKey = request.getHeader("Authorization");
			/* test */ authKey = "L9+BpDHrub+WsyPGL3Zp3k60jG5+ddMGIxrlBD6q/NLNZCvvdYGBNarY/eERG5C6";					
			String aprsId = appManagerService.insertAppraisalDetail(parameter, appId, authKey);
		} catch (Exception ex) {
			return authFail(ex.getMessage());
		}
		
		result.setSuccess(true);
		result.setMessage("평가완료");
		result.setAppId(appId);
		
		return success(result);
	}

	private int getCalcuTotPage(long rowCount, int nSplitSize) {
		int nValue = (int)(rowCount % nSplitSize);
		int nPageValue = (int) Math.floor(rowCount / nSplitSize);
		
		if(nValue > 0) {
			nPageValue += 1;
		}
		return nPageValue;
	}
}