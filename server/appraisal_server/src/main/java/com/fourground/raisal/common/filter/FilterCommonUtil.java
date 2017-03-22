package com.fourground.raisal.common.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class FilterCommonUtil {
    public static boolean isAnonyAccessible(HttpServletRequest request,String[] anonymousAccessibleResources){
    	PathMatcher pathMatcher = new AntPathMatcher();
        String url = request.getRequestURI();
		for(String path : anonymousAccessibleResources) {
	        if(pathMatcher.match(path, url)) {
	        	return true;
	        }
		}
		return false;
    }
}
