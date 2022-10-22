package com.gmail.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gmail.constant.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenGeneratorFiltor extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Inside token generator filter 1");
		if (authentication!=null) {
			Date iatDate=new Date();
			Date expDate=new Date();
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(expDate);
			calendar.add(calendar.HOUR, 4);
			
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			System.out.println("Inside token generator filter 2");	
			String jwt = Jwts.builder().setIssuer("Gmail").setSubject("JWT Token")
						.claim("username", authentication.getName())
					  .claim("authorities", authentication.getAuthorities())
					  .setIssuedAt(iatDate)
					.setExpiration(calendar.getTime())
					.signWith(key).compact();
			System.out.println(jwt);
			response.setHeader(SecurityConstants.JWT_HEADER, jwt);
		}

		filterChain.doFilter(request, response);
		
	}
	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return  !request.getServletPath().equals("/mail/login");
	}
}
