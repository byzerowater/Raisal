package com.fourground.raisal.user.dao;

import java.util.Map;

import com.fourground.raisal.user.dto.AuthInfoVo;
import com.fourground.raisal.user.dto.MyChnlInfoVo;

public interface IChannelAccountDao {

	public AuthInfoVo getAuthkey(String userId);
	public AuthInfoVo getAuthKeyMap(Map<String, Object> parameter);
	public MyChnlInfoVo getMyInfo(String userId);
	public int insertChnlAccnt(Map<String, Object> parameter);
	public int updateChnlAccntByUserid(Map<String, Object> parameter);
	public int deleteChnlAccnt(Map<String, Object> parameter);
}
