package com.fourground.raisal.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fourground.raisal.common.exception.AccessDeniedException;

public class AnonymousAccessFilter extends BaseWebSecurityFilter {
	
    private String[] anonymousAccessibleResources = new String[0];
    
    public AnonymousAccessFilter() {
    	String[] resourcePath = {"/api/auth/*","/v2/*","/swagger*","/configuration/*"};
    	this.anonymousAccessibleResources = resourcePath;
    }
    
	@Override
	protected void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		boolean isAnnonyAccessible = FilterCommonUtil.isAnonyAccessible(request, anonymousAccessibleResources);
		if(!isAnnonyAccessible /*&& userContainer.isAnonymous()*/) {
			// authorizing api
			String authKey = request.getHeader("authKey");
//			if(authKey == null) {
//				String msg = "unknown user's request[path=" + request.getRequestURI() + "] is denied;";
//				System.out.println(request.getRequestURI());
//				throw new AccessDeniedException(msg);
//			} else {
//				// next
//			}
		} else {
			// non-authorizing api
			// next
		}
		chain.doFilter(request, response);
	}
}
