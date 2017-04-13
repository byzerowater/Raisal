package com.fourground.raisal.user.svc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fourground.raisal.common.AES256Util;
import com.fourground.raisal.common.Constants;
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
				String randomNick = generateRandomNick(userUid);
				
				if(randomNick == null || randomNick.length() < 10) {
					throw new Exception("Don't generate Nickname.");
				}
				
				parameter.put("userNm", randomNick);
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
		try {
			if(!validateNickName(userNickNm)) {
				throw new Exception("Invalid policy for nick name");
			}
		} catch (Exception ex) {
			throw new Exception("Invalid policy for nick name :" + ex.getMessage());
		}
		if(!duplicateNickName(userNickNm)) {
			throw new Exception("Duplicate nick name");
		}
		
		AuthInfoVo authInfo = this.getAuthInfo(parameter);
		if(authInfo != null) {
			String userId = authInfo.getUserId();
			if(userId != null && userId.length() > 0) {
				parameter.put("userUid", userId);
				parameter.remove("authKey");
			} else {
				throw new Exception("Invalid authkey. Retry login.");
			}
		} else {
			throw new Exception("Fail authorization");
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
	private boolean validateNickName(String nickNm) throws Exception {
		if(nickNm == null) {
			throw new Exception("Nick name is empty");
		}
		
		if(nickNm.length() > 10) {
			throw new Exception("Nick name is too long");
		}
		
		if(!nickNm.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")){
			throw new Exception("Cannot use special character in your nick.");
		}
		
		return true;
	}
	
	private boolean duplicateNickName(String nickNm) {
		int nCount = chnlAccntDao.getCountMatchingNick(nickNm);
		return !(nCount > 0);
	}
	
	private String generateRandom(String userId) {
		Random rnd =new Random();
		StringBuffer rtnNick = new StringBuffer();

		for(int i=0;i<10;i++){
		    if(rnd.nextBoolean()){
		    	rtnNick.append((char)((int)(rnd.nextInt(26))+97));
		    }else{
		    	rtnNick.append((rnd.nextInt(10))); 
		    }
		}
//		if(userId != null && userId.length() > 3) {
//			buf.append(userId.substring(userId.length()-3));
//		}
		if(rtnNick.toString() != null) {
			return rtnNick.toString().toUpperCase();
		} else {
			return rtnNick.toString();
		}
	}
	
	private String generateRandomNick(String userId) {

		String rndNick = "";
		boolean bUniq = false;
		
		int nTryCnt = 0;
		
		// DB에 있는지 검사
		while(!bUniq && nTryCnt < 5) {
			// 랜덤생성
			rndNick = generateRandom(userId);
			
			bUniq = this.duplicateNickName(rndNick);
			if(!bUniq) {
				nTryCnt++;
			} else {
				break;
			}
		}
		
		return rndNick;
	}
	
	private String generateLoginAuthKey(String userId) {
		String authKey = null;
		
		try {
			authKey = getAESEncrypt(userId);
		} catch (Exception ex) {
		}
//		return authKey;
		// development
		return "L9+BpDHrub+WsyPGL3Zp3k60jG5+ddMGIxrlBD6q/NLNZCvvdYGBNarY/eERG5C6";
	}
	
	private String getAESEncrypt(String source) throws Exception {
		AES256Util aes256 = new AES256Util(Constants.aesKey);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy$yyMM$dd$HH$mm$ss$");
		Date now = new Date();
		
		String sourceData = source + sdf.format(now);
		return aes256.aesEncode(sourceData);
	}
}		
