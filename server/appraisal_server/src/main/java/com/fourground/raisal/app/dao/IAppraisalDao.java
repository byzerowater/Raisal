package com.fourground.raisal.app.dao;

import java.util.List;
import java.util.Map;

import com.fourground.raisal.app.dto.AppInfoDetailVo;
import com.fourground.raisal.app.dto.AppInfoVo;
import com.fourground.raisal.app.dto.AppraisalVo;

public interface IAppraisalDao {

	public AppInfoDetailVo getAppDetailInfo(Map<String, Object> parameter);
	public List<AppInfoVo> selectAppInfoList(Map<String, Object> parameter);
	public Long selectAppInfoListCount(Map<String, Object> parameter);

	public List<AppraisalVo> selectAppraisalList(Map<String, Object> parameter);
	public Long selectAppraisalCount(Map<String, Object> parameter);
}
