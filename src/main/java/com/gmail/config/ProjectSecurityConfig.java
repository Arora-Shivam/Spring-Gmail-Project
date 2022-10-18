package com.gmail.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.gmail.filter.JwtTokenGeneratorFiltor;
import com.gmail.filter.JwtTokenValidatorFilter;

@Configuration
public class ProjectSecurityConfig {


	@Bean
	public SecurityFilterChain gmailUserConfig(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.addFilterAfter(new JwtTokenGeneratorFiltor(),BasicAuthenticationFilter.class)
		.authorizeHttpRequests(auth -> {
			try {
				auth.antMatchers("/mail/admin/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.POST, "/mail/register").permitAll()
					.antMatchers(HttpMethod.DELETE, "/mail/user").authenticated()
					.antMatchers("/login","/swagger-ui/**")
						.permitAll()
						.antMatchers("/mail/send","/mail/attachment/**", "/mail/draft", "/mail/trash/**","/mail/restore/**","/mail/search/**", "/mail/starred/**", "mail/emptyTrash",
									  "/mail/admin/**","/mail/inbox","/mail/sent","/mail/starred","/mail/draft","/mail/trash","/mail/allMail","/mail/login")
						.authenticated().and().csrf().disable()
						.logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/end");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).httpBasic(Customizer.withDefaults());

		return httpSecurity.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
