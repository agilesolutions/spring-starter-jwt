package com.agilesolutions.security.template;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.agilesolutions.security.util.JwtTokenUtil;

@Configuration
public class JWTRestTemplate {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Bean
	public RestTemplate restTemplate() {

		RestTemplate restTemplate = new RestTemplate();

		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();

		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<>();
		}

		interceptors.add(new RestTemplateHeaderModifierInterceptor());

		restTemplate.setInterceptors(interceptors);

		return restTemplate;
	}

}