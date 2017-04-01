package com.fourground.raisal.user.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fourground.raisal.user.dto.AuthInfoVo;
import com.fourground.raisal.user.dto.MyChnlInfoVo;

@Repository
public class ChannelAccountDao {
	private final String PREFIX = this.getClass().getName()+".";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public AuthInfoVo getAuthkey(String userId) {
		return sqlSession.selectOne(PREFIX+"getAuthkey", userId);
	}
	public AuthInfoVo getAuthKeyByParam(Map<String, Object> parameter) {
		return sqlSession.selectOne(PREFIX+"getAuthkeyMap", parameter);
	}
	public MyChnlInfoVo getMyInfo(String userId) {
		return sqlSession.selectOne(PREFIX+"getMyInfo", userId);
	}
	public int insertManager(Map<String, Object> parameter){
		return sqlSession.insert(PREFIX+"insertChnlAccnt", parameter);
	}
	public int updateManager(Map<String, Object> parameter){ 
		return sqlSession.update(PREFIX+"updateChnlAccntByUserid", parameter);
	}
	public int deleteManager(Map<String, Object> parameter){
		return sqlSession.delete(PREFIX+"deleteChnlAccnt", parameter);
	}
}
