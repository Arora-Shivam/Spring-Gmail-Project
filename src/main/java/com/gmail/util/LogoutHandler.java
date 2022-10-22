package com.gmail.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Configuration
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler{

	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		
		Cookie deleteJwtCookie=new Cookie("Authorization", null);
		response.addCookie(deleteJwtCookie);
	}

}
