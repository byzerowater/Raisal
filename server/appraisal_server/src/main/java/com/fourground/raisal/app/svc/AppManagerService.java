package com.fourground.raisal.app.svc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fourground.raisal.app.dao.IAppraisalDao;
import com.fourground.raisal.app.dto.AppInfoDetailVo;
import com.fourground.raisal.app.dto.AppInfoVo;
import com.fourground.raisal.app.dto.AppraisalVo;
import com.fourground.raisal.user.dao.IChannelAccountDao;
import com.fourground.raisal.user.dto.AuthInfoVo;

@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
@Service
public class AppManagerService {

	@Autowired
	private IAppraisalDao appraisalDao;
	
	@Autowired
	private IChannelAccountDao channelAccountDao;

	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public AppInfoDetailVo getAppDetail(Map<String, Object> parameter) {
		AppInfoDetailVo appDetail = new AppInfoDetailVo();
		appDetail = appraisalDao.getAppDetailInfo(parameter);
		return appDetail;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public List<AppInfoVo> selectAppList(Map<String, Object> parameter) {
		List<AppInfoVo> appInfoList = new ArrayList<AppInfoVo>();
		appInfoList = appraisalDao.selectAppInfoList(parameter);
		return appInfoList;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public long selectAppListCount(Map<String, Object> parameter) {
		Long listCnt = appraisalDao.selectAppInfoListCount(parameter);
		return listCnt != null ? listCnt.longValue() : 0L;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public List<AppraisalVo> selectAppraisalList(Map<String, Object> parameter) {
		List<AppraisalVo> appraisalVoList = new ArrayList<AppraisalVo>();
		appraisalVoList = appraisalDao.selectAppraisalList(parameter);
		return appraisalVoList;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public long selectAppraisalListCount(Map<String, Object> parameter) {
		Long listCnt = appraisalDao.selectAppraisalCount(parameter);
		return listCnt != null ? listCnt.longValue() : 0L;
	}
	

	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public String insertAppraisalDetail(Map<String, Object> parameter, String appId, String authKey) throws Exception {
//		Map<String, Object> raisalPoint = (Map<String, Object>)parameter.get("raisalPoint");
//		String useTerm = (String)parameter.get("useTerm");
//		String platformCode = (String)parameter.get("platformCode");
		
		Map<String, Object> authParam = new HashMap<String, Object>();
		authParam.put("authKey", authKey);
		AuthInfoVo authInfoVo = channelAccountDao.getAuthKeyMap(authParam);
		// get userid
		String userId = authInfoVo.getUserId();
		
		if(userId == null || userId.length() <= 0) {
			throw new Exception("Not found user.");
		}
		parameter.put("userId", userId);
		parameter.put("appId", appId);

		//
		String aprsId = appraisalDao.getSeq("CO_APPR");
		parameter.put("aprsId", aprsId);
		
		appraisalDao.insertAppraisalDetail(parameter);
		
		
		String apprId = appraisalDao.getSeq("CO_APPR_RPLY");
		parameter.put("apprId", apprId);
		
		appraisalDao.insertAppraisalReply(parameter);
		
		return aprsId;
	}
	
	public AuthInfoVo getAuthInfo(Map<String, Object> parameter) {
		// 로그인 방식에 따라 둘 중 선택
		// return chnlAccntDao.getAuthkey((String)parameter.get("userUid"));
		return channelAccountDao.getAuthKeyMap(parameter);
	}
	
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
	public String registAppInfo(Map<String, Object> parameter, String authKey) throws Exception {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> downloadInfoList = (List<Map<String,Object>>)parameter.get("downInfo");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		
		// term calcu
		String strDtm = sdf.format(today);
		String endDtm = getEndDtm(today, (String)parameter.get("reqTerm"));
		
		Map<String, Object> authParam = new HashMap<String, Object>();
		authParam.put("authKey", authKey);
		AuthInfoVo authInfoVo = channelAccountDao.getAuthKeyMap(authParam);
		// get userid
		String userId = authInfoVo.getUserId();
		
		if(userId == null || userId.length() <= 0) {
			throw new Exception("Not found user.");
		}
		// get appId
		String appId = appraisalDao.getSeq("CO_APPR_APP");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		param.put("appNm", parameter.get("appName"));
		param.put("appTitle", parameter.get("title"));
		param.put("appDesc", parameter.get("appDesc"));
		param.put("apprStus", "A");	// Active
		param.put("aprsStartDtm", strDtm);
		param.put("aprsEndDtm", endDtm);
		param.put("userId", userId);

		int nInsertCnt = 0;
		nInsertCnt = appraisalDao.insertAppMasterInfo(param);
		
		if(downloadInfoList != null && downloadInfoList.size() > 0) {
			for(Map<String, Object> mapTemp : downloadInfoList) {
				param.put("plfmCd", (String)mapTemp.get("platformCode"));
				param.put("refUrl", (String)mapTemp.get("downUrl"));
				param.put("refCd", "DU");	// Download Url

				appraisalDao.insertRefUrl(param);
			}
		}
		
		return appId;
	}
	
	private boolean duplicateActiveApp(String downloadUrl) {
		
		return true;
	}
	
	private String getEndDtm(Date today, String aprsTerm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int nTermDays = 1;
		if(aprsTerm != null) {
			try {
				nTermDays = Integer.parseInt(aprsTerm);
			} catch (Exception ex) {
			}
		}

		// today
		Calendar calTemp = Calendar.getInstance( );
		calTemp.setTime(today);
		
		// calculate
		calTemp.add(Calendar.DATE, nTermDays);

		return sdf.format(new Date(calTemp.getTimeInMillis()));
	}
}
