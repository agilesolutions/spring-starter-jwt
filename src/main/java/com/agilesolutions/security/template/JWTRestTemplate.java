package com.agilesolutions.security.template;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Component
public class JWTRestTemplate extends RestTemplate {
	
	

	public JWTRestTemplate() {
		super();
		List<ClientHttpRequestInterceptor> interceptors = super.getInterceptors();

		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<>();
		}

		interceptors.add(new RestTemplateHeaderModifierInterceptor());

		super.setInterceptors(interceptors);
	}


}