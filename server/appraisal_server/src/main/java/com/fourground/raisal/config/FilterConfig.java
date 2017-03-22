package com.fourground.raisal.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fourground.raisal.common.filter.AnonymousAccessFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean getFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean(new AnonymousAccessFilter());
		return registrationBean;
	}
}
