package com.fourground.raisal.app.svc;

import java.util.ArrayList;
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

@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
@Service
public class AppManagerService {

	@Autowired
	private IAppraisalDao appraisalDao;

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
}
