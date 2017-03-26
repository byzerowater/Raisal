package com.fourground.raisal.user.svc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fourground.raisal.user.dao.ChannelAccountDao;
import com.fourground.raisal.user.dto.AuthInfoVo;
import com.fourground.raisal.user.dto.MyChnlInfoVo;

@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
@Service
public class AuthManagerService {

	@Autowired
	private ChannelAccountDao chnlAccntDao;
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public AuthInfoVo generateAuthKeyForUser(Map<String, Object> parameter) {
		String userUid = (String)parameter.get("userUid");
		
		// 인증키 생성
		String authKey = generateLoginAuthKey(userUid);
		
		// 기존에 등록된 회원인지 가져온다.		
		AuthInfoVo myAccntInfo = this.getAuthInfo(parameter);
		
		parameter.put("authKey", authKey);
		try {
			int nRowCnt = 0;
			if(myAccntInfo != null && myAccntInfo.getUserId().length() > 0) {
				// 기존회원이면 UPDATE
				nRowCnt = chnlAccntDao.updateManager(parameter);
			} else {
				// 신규회원이면 INSERT
				parameter.put("userId", generateRandomUserId(parameter));
				
				nRowCnt = chnlAccntDao.insertManager(parameter);
				myAccntInfo =  this.getAuthInfo(parameter);
			}
			if(nRowCnt == 1) {
				myAccntInfo.setAuthKey(authKey);
			} else {
				// 인증키 업데이트 오류
			}
		} catch(Exception ex) {
			// 인증키 업데이트 실패
		}
		
		return myAccntInfo;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public MyChnlInfoVo getMyInfo(String userId) {
		return chnlAccntDao.getMyInfo(userId);
	}
	
	private AuthInfoVo getAuthInfo(Map<String, Object> parameter) {
		// 로그인 방식에 따라 둘 중 선택
		// return chnlAccntDao.getAuthkey((String)parameter.get("userUid"));
		return chnlAccntDao.getAuthKeyByParam(parameter);
	}
	
	private String generateRandomUserId(Map<String, Object> parameter) {
		return "project4ground-random";
	}
	
	private String generateLoginAuthKey(String userId) {
		return "L9+BpDHrub+WsyPGL3Zp3k60jG5+ddMGIxrlBD6q/NLNZCvvdYGBNarY/eERG5C6";
	}
}
