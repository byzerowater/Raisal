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
import com.fourground.raisal.user.dto.AuthInfoVo;
import com.fourground.raisal.user.svc.AuthManagerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Login API Contoller")
@Controller
@RequestMapping(value="/api/auth", produces= MediaType.APPLICATION_JSON_VALUE)
public class LoginRestController extends BaseRestController {
	
	@Autowired
	private AuthManagerService authManageService;

	@ApiOperation(value="로그인 API"
			,notes="1>인증키 발급"
			,response=AuthInfoVo.class)
	@RequestMapping(value="/get", method={RequestMethod.POST})
	public ResponseEntity<Object> getUserAuthKey(
			HttpServletRequest request
			,@ApiParam(name="body",value="\"userUid\":\"채널사고유UID\","+BR+"\"channelCode\":\"채널사코드(G,F)\","+BR+"\"email\":\"등록이메일\""+BR) @RequestBody RequestBodyVo requestBody)
	{
		Map<String, Object> parameter = requestBody.getBody();
		
		AuthInfoVo authInfoVo = null;
		
		try {
			authInfoVo = authManageService.generateAuthKeyForUser(parameter);
		} catch(Exception ex) {
			return dbFail(ex.getMessage());
		}
		
		return success(authInfoVo);
	}
}