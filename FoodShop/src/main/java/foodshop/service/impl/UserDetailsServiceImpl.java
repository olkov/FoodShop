package foodshop.service.impl;

import org.springframework.security.core.userdetails.User.UserBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import foodshop.entity.User;
import foodshop.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.getUserByUserName(userName);
		if (user != null) {
			UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(userName);
			builder.password(user.getPassword());
			builder.roles(user.getRoles().stream().map(r -> r.getName()).toArray(String[]::new));
			return builder.build();
		}
		System.err.println("User \"" + userName + "\" not found!");
		throw new UsernameNotFoundException("User \"" + userName + "\" not found!");
	}
}