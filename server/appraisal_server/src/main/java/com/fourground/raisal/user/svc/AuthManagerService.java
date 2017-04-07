package com.fourground.raisal.user.svc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fourground.raisal.user.dao.IChannelAccountDao;
import com.fourground.raisal.user.dto.AuthInfoVo;
import com.fourground.raisal.user.dto.MyChnlInfoVo;

@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
@Service
public class AuthManagerService {

	@Autowired
	private IChannelAccountDao chnlAccntDao;
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public AuthInfoVo generateAuthKeyForUser(Map<String, Object> parameter) throws Exception {
		String userUid = (String)parameter.get("userUid");
		
		if(userUid == null || userUid.length() <= 0) {
			throw new Exception("채널ID가 없습니다");
		}
		
		// 인증키 생성
		String authKey = generateLoginAuthKey(userUid);
		
		// 기존에 등록된 회원인지 가져온다.		
		AuthInfoVo myAccntInfo = this.getAuthInfo(parameter);
		
		parameter.put("authKey", authKey);
		try {
			int nRowCnt = 0;
			if(myAccntInfo != null && myAccntInfo.getUserId().length() > 0) {
				// 기존회원이면 UPDATE
				nRowCnt = chnlAccntDao.updateChnlAccntByUserid(parameter);
			} else {
				nRowCnt = chnlAccntDao.insertChnlAccnt(parameter);
				myAccntInfo =  this.getAuthInfo(parameter);
			}
			if(nRowCnt == 1) {
				myAccntInfo.setAuthKey(authKey);
			} else {
				// 인증키 업데이트 오류
			}
		} catch(Exception ex) {
			// 인증키 업데이트 실패
			throw new Exception(ex);
		}
		
		return myAccntInfo;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public MyChnlInfoVo getMyInfo(Map<String, Object> parameter) {
		return chnlAccntDao.getMyInfo(parameter);
	}
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public MyChnlInfoVo updateMyInfo(Map<String, Object> parameter) throws Exception {
		
		String userNickNm = (String)parameter.get("userNm");
		
		// nickname 무결성 체크 (중복체크 포함)
		if(!validateNickName(userNickNm)) {
			throw new Exception("Invalid policy for nick name");
		}
		if(!duplicateNickName(userNickNm)) {
			throw new Exception("Duplicate nick name");
		}
		
		int nCnt = chnlAccntDao.updateChnlAccntByUserid(parameter);
		if(nCnt < 0) {
			throw new Exception("Fail to DB Update");
		}
		
		return chnlAccntDao.getMyInfo(parameter);
	}

	private AuthInfoVo getAuthInfo(Map<String, Object> parameter) {
		// 로그인 방식에 따라 둘 중 선택
		// return chnlAccntDao.getAuthkey((String)parameter.get("userUid"));
		return chnlAccntDao.getAuthKeyMap(parameter);
	}
	
	// nick validation
	private boolean validateNickName(String nickNm) {
		if(nickNm == null) {
			return false;
		}
		return true;
	}
	
	private boolean duplicateNickName(String nickNm) {
		return true;
	}

	private String generateLoginAuthKey(String userId) {
		return "L9+BpDHrub+WsyPGL3Zp3k60jG5+ddMGIxrlBD6q/NLNZCvvdYGBNarY/eERG5C6";
	}
}
