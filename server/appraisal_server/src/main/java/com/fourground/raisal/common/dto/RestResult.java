package com.fourground.raisal.common.dto;

import java.util.HashMap;
import java.util.Map;

import com.fourground.raisal.common.Constants;

public class RestResult extends BaseModel {

	private static final long serialVersionUID = 7054985785308631595L;

	private Map<String,Object> meta;
	private Object data;
	private PageLink links;
	
	public void init(Object data) {
		if(data.getClass().equals(HashMap.class)) {
			@SuppressWarnings("unchecked")
			Map<String, Object>	map = (Map<String, Object>) data;
			
			// set meta
			meta = new HashMap<String, Object>();
			meta.put("total-pages", (int)map.get("totPages"));
			
			// set data
			this.data = map.get("data");
			
			// set pagelink
			int size = map.get("size") != null ? ((Integer)map.get("size")).intValue() : Constants.Search.nMinSearchCnt;
			int currPage = map.get("currPage") != null ? ((Integer)map.get("currPage")).intValue() : 1;
			
			links = new PageLink((String)map.get("baseUrl"), currPage, (int)map.get("totPages"), size);
		} else {
			// set data
			this.data = data;
		}
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public PageLink getLinks() {
		return links;
	}

	public void setLinks(PageLink links) {
		this.links = links;
	}
}
