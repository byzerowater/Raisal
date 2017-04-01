package com.fourground.raisal.common.dto;

public class PageLink {
	
	private String baseApiUrl;

	private String self;
	private String first;
	private String prev;
	private String next;
	private String last;
	
	public PageLink(String url, int nCurrPage, int nTotPage, int nSize) {
		baseApiUrl = url;
		
		self = String.format("%s?page=%d&size=%d", baseApiUrl, nCurrPage, nSize);

		if(nCurrPage > 1) {
			first = String.format("%s?page=1&size=%d", baseApiUrl, nSize);
			prev = String.format("%s?page=%d&size=%d", baseApiUrl, nCurrPage-1, nSize);
		} else {
			first = null;
			prev = null;
		}
		if(nCurrPage < nTotPage) {
			next = String.format("%s?page=%d&size=%d", baseApiUrl, nCurrPage+1, nSize);
			last = String.format("%s?page=%d&size=%d", baseApiUrl, nTotPage, nSize);
		} else {
			next = null;
			last = null;
		}
	}
	
	public String getSelf() {
		return self;
	}

	public String getFirst() {
		return first;
	}

	public String getPrev() {
		return prev;
	}

	public String getNext() {
		return next;
	}

	public String getLast() {
		return last;
	}
}
