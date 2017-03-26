package com.fourground.raisal.user.ctl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fourground.raisal.common.ctl.BaseRestController;
import com.fourground.raisal.common.dto.RequestBodyVo;
import com.fourground.raisal.user.dto.MyChnlInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Account API Contoller")
@Controller
@RequestMapping(value="/api/account", produces= MediaType.APPLICATION_JSON_VALUE)
public class AccountRestController extends BaseRestController {

	@ApiOperation(value="어카운트 API"
			,notes="1>내정보 가져오기"
			,response=MyChnlInfoVo.class)
	@RequestMapping(value="/get", method={RequestMethod.POST})
	public ResponseEntity<Object> getMyInfo(
			HttpServletRequest request
			,@ApiParam(name="body", value="\"email\":\"등록이메일\"") @RequestBody RequestBodyVo requestBody)
	{
		MyChnlInfoVo myInfoVo = new MyChnlInfoVo();
		
		myInfoVo.setChannelCode((String)requestBody.getBody().get("chnCode"));
		myInfoVo.setEmail("project4ground@gmail.com");
		myInfoVo.setNickName("멋진남자");
		myInfoVo.setRegAppCount("3");
		myInfoVo.setUserId("project4ground-random");
		
		return success(myInfoVo);
	}
}
