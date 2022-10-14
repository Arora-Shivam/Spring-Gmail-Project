package com.gmail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class ProjectSecurityConfig {


	@Bean
	public SecurityFilterChain gmailUserConfig(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeHttpRequests(auth -> {
			try {
				auth.antMatchers("/mail/admin/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.POST, "/mail/user").permitAll()
					.antMatchers(HttpMethod.DELETE, "/mail/user").authenticated()
					.antMatchers("/login","/swagger-ui/**")
						.permitAll()
						.antMatchers("/mail/send","/mail/attachment/**", "/mail/draft", "/mail/trash/**","/mail/restore/**","/mail/search/**", "/mail/starred/**", "mail/emptyTrash",
									  "/mail/admin/**","/mail/inbox","/mail/sent","/mail/starred","/mail/draft","/mail/trash","/mail/allMail")
						.authenticated().and().csrf().disable().formLogin().loginProcessingUrl("/login").and()
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
