package com.fourground.raisal.user.ctl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fourground.raisal.common.ctl.BaseRestController;
import com.fourground.raisal.common.dto.RequestBodyVo;
import com.fourground.raisal.user.dto.MyChnlInfoVo;
import com.fourground.raisal.user.svc.AuthManagerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Account API Contoller")
@Controller
@RequestMapping(value="/api/account", produces= MediaType.APPLICATION_JSON_VALUE)
public class AccountRestController extends BaseRestController {

	@Autowired
	private AuthManagerService authManageService;

	@ApiOperation(value="어카운트 API"
			,notes="1>내정보 가져오기"
			,response=MyChnlInfoVo.class)
	@RequestMapping(value="/get", method={RequestMethod.POST})
	public ResponseEntity<Object> getMyInfo(
			HttpServletRequest request
			,@ApiParam(name="body", value="\"userUid\":\"채널사고유UID\"") @RequestBody RequestBodyVo requestBody)
	{
		Map<String, Object> parameter = requestBody.convertToMap();
		MyChnlInfoVo myInfoVo = null;
		
		try {
			myInfoVo = authManageService.getMyInfo(parameter);
		} catch(Exception ex) {
			return dbFail(ex.getMessage());
		}
		
		return success(myInfoVo);
	}
	
	@ApiOperation(value="어카운트 API"
			,notes="1>내 닉네임 수정하기"
			,response=MyChnlInfoVo.class)
	@RequestMapping(value="/update", method={RequestMethod.POST})
	public ResponseEntity<Object> updateMyNick(
			HttpServletRequest request
			,@ApiParam(name="body", value="\"userNm\":\"닉네임\"") @RequestBody RequestBodyVo requestBody) {
		
		Map<String, Object> parameter = requestBody.convertToMap();
		MyChnlInfoVo myInfoVo = null;
		
		try {
			String authKey = request.getHeader("Authorization");
			/* test */ authKey = "L9+BpDHrub+WsyPGL3Zp3k60jG5+ddMGIxrlBD6q/NLNZCvvdYGBNarY/eERG5C6";
			if(authKey != null && authKey.length() > 0) {
				parameter.put("authKey", authKey);
			} else {
				return authFail("세션이 만료 됐습니다. 다시 로그인 해 주세요.");
			}
		} catch (Exception e) {
			return authFail("세션이 만료 됐습니다. 다시 로그인 해 주세요.");
		}
		
		try {
			myInfoVo = authManageService.updateMyInfo(parameter);
		} catch (Exception e) {
			try {
				myInfoVo = authManageService.getMyInfo(parameter);
			} catch(Exception ex) {
				return dbFail(ex.getMessage());
			}
			return fail(myInfoVo, e.getMessage());
		}
		
		return success(myInfoVo);
	}
	
	// 내가 요청한 평가 목록
	
	// 내가 쓴 평가 목록
	
}
