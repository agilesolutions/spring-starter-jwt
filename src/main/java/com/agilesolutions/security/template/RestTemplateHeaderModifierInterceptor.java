package com.agilesolutions.security.template;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.agilesolutions.security.util.JwtTokenUtil;

public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		ClientHttpResponse response = execution.execute(request, body);

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

		final String token = jwtTokenUtil.generateToken(userDetails);

		response.getHeaders().add("Authorization", "Bearer " + token);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);;
		return response;
	}
}
