package com.gmail.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.gmail.exception.UserAlreadyExistException;
import com.gmail.exception.UserNotFoundException;
import com.gmail.module.User;
import com.gmail.repository.UserDao;

public class GmailUserNamePasswordAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserDao userDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		Optional<User> optUser = userDao.findByEmail(authentication.getName());

		if (optUser.isPresent()) {

			User user = optUser.get();
			SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(optUser.get().getRole());
			String authenticationPassword = authentication.getCredentials().toString();

			if (passwordEncoder.matches(authenticationPassword, user.getPassword())) {
				List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
				grantedAuthorities.add(simpleGrantedAuthority);
				System.out.println(grantedAuthorities);
				return new UsernamePasswordAuthenticationToken(user.getEmail(), authenticationPassword,
						grantedAuthorities);
			} else {
				throw new UserAlreadyExistException("Bad Credentials");
			}

		} else {

			throw new UserNotFoundException("User does not exist");
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
