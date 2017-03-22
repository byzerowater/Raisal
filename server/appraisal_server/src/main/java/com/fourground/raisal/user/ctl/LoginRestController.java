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
import com.fourground.raisal.user.dto.AuthInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="Login API Contoller")
@Controller
@RequestMapping(value="/api/auth", produces= MediaType.APPLICATION_JSON_VALUE)
public class LoginRestController extends BaseRestController {

	@ApiOperation(value="로그인 API"
			,notes="1>인증키 발급"
			,response=AuthInfoVo.class)
	@RequestMapping(value="/get", method={RequestMethod.POST})
	public ResponseEntity<Object> getUserAuthKey(
			HttpServletRequest request
			,@ApiParam(name="body",value="\"userUid\":\"채널사고유UID\","+BR+"\"channelCode\":\"채널사코드(G,F)\","+BR+"\"email\":\"등록이메일\""+BR) @RequestBody RequestBodyVo requestBody)
	{
		AuthInfoVo authInfoVo = new AuthInfoVo();
		
		authInfoVo.setAuthKey("L9+BpDHrub+WsyPGL3Zp3k60jG5+ddMGIxrlBD6q/NLNZCvvdYGBNarY/eERG5C6");
		authInfoVo.setChannelCode((String)requestBody.getBody().get("chnCode"));
		authInfoVo.setEmail("project4ground@gmail.com");
		authInfoVo.setNickName("멋진남자");
		authInfoVo.setRegAppCount("3");
		authInfoVo.setUserId("project4ground-random");
		
		return success(authInfoVo);
	}
}