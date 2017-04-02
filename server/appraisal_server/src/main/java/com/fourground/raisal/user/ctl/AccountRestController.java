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
		Map<String,Object> parameter = requestBody.getBody();

		MyChnlInfoVo myInfoVo = new MyChnlInfoVo();
		
		try {
			myInfoVo = authManageService.getMyInfo(parameter);
		} catch(Exception ex) {
			return dbFail(ex.getMessage());
		}
		
		return success(myInfoVo);
	}
}
