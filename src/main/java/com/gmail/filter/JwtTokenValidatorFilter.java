package com.gmail.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gmail.constant.SecurityConstants;
import com.gmail.exception.UserAlreadyExistException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenValidatorFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
		String jwt=this.getJwtCookieValue(request, "Authorization");
		
		if (null != jwt) {
			try {
				
				SecretKey key = Keys.hmacShaKeyFor(
						SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
				
				
				Claims claims = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(jwt)
						.getBody();
				
				
				
				String username = String.valueOf(claims.get("username"));
				System.out.println(username);
				
				System.out.println(claims);
				
				String autorities= String.valueOf( claims.get("authorities"));
				
				List<GrantedAuthority> a=AuthorityUtils.commaSeparatedStringToAuthorityList(autorities);
				
				Authentication auth = new UsernamePasswordAuthenticationToken(username,null,a);
				
				
				
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			}
			catch (Exception e) {
				throw new UserAlreadyExistException("Invalid Token received!");
			}
			
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().equals("/mail/login");
	}
	
	private String getJwtCookieValue(HttpServletRequest req, String cookieName) {
	    return Arrays.stream(req.getCookies())
	            .filter(c -> c.getName().equals(cookieName))
	            .findFirst()
	            .map(Cookie::getValue)
	            .orElse(null);
	}
}
